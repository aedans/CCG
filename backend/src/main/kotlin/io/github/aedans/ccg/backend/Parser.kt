package io.github.aedans.ccg.backend

@Suppress("MemberVisibilityCanBePrivate")
object Parser {
    interface Parser<out R, T> {
        operator fun invoke(input: Sequence<T>): Result<R, T>

        companion object {
            inline operator fun <R, T> invoke(crossinline fn: (Sequence<T>) -> Result<R, T>) = object : Parser<R, T> {
                override fun invoke(input: Sequence<T>) = fn(input)
            }
        }
    }

    @Suppress("unused")
    sealed class Result<out R, out T> {
        class Failure() : Result<Nothing, Nothing>()
        data class Success<out R, out T>(val value: R, val rest: Sequence<T>) : Result<R, T>()
    }

    fun <T> predicateParser(predicate: (T) -> Boolean) = Parser<T, T> { input ->
        if (input.any() && predicate(input.first()))
            Result.Success(input.first(), input.drop(1))
        else
            Result.Failure()
    }

    fun <A, B, T> mapParser(parser: Parser<A, T>, fn: (Result<A, T>) -> Result<B, T>) = Parser<B, T> { input  ->
        fn(parser(input))
    }

    fun <A, B, T> mapParserSuccess(parser: Parser<A, T>, fn: (Result.Success<A, T>) -> Result.Success<B, T>): Parser<B, T> =
            mapParser(parser) { if (it is Result.Success) fn(it) else it as Result.Failure }

    fun <A, B, T> mapParserResult(parser: Parser<A, T>, fn: (A) -> B) =
            mapParserSuccess(parser) { Result.Success(fn(it.value), it.rest) }

    fun <A, B, T> combineParser(parser1: Parser<A, T>, parser2: Parser<B, T>) = Parser<Pair<A, B>, T> { input ->
        val result1 = parser1(input)
        when (result1) {
            is Result.Success -> {
                val result2 = parser2(result1.rest)
                when (result2) {
                    is Result.Success -> Result.Success(result1.value to result2.value, result2.rest)
                    is Result.Failure -> result2
                }
            }
            is Result.Failure -> result1
        }
    }

    fun <R, T> combineParserLeft(parser1: Parser<R, T>, parser2: Parser<*, T>) =
            mapParserResult(combineParser(parser1, parser2), Pair<R, *>::first)

    fun <R, T> combineParserRight(parser1: Parser<*, T>, parser2: Parser<R, T>) =
            mapParserResult(combineParser(parser1, parser2), Pair<*, R>::second)

    fun <R, T> repeatParser(parser: Parser<R, T>): Parser<Sequence<R>, T> = Parser { input ->
        val result = parser(input)
        when (result) {
            is Result.Success -> {
                val restResult = repeatParser(parser)(result.rest) as Result.Success<Sequence<R>, T>
                Result.Success(sequenceOf(result.value) + restResult.value, restResult.rest)
            }
            is Result.Failure -> Result.Success(emptySequence(), input)
        }
    }

    fun <R, T> repeatParser1(parser: Parser<R, T>): Parser<Sequence<R>, T> =
            mapParserResult(combineParser(parser, repeatParser(parser))) { (head, tail) -> sequenceOf(head) + tail }

    fun <R, T> alternativeParser(parser1: Parser<R, T>, parser2: Parser<R, T>) = Parser<R, T> { input ->
        val result1 = parser1(input)
        when (result1) {
            is Result.Success -> result1
            is Result.Failure -> parser2(input)
        }
    }

    fun <R, T> lazyParser(parser: () -> Parser<R, T>) = Parser<R, T> { input ->
        parser()(input)
    }

    fun isWhitespace(c: Char) = isNewline(c) || c in " \t"
    fun isNewline(c: Char) = c in "\r\n"

    val newlineParser: Parser<Char, Char> = predicateParser(this::isNewline)
    val whitespaceParser: Parser<Char, Char> = alternativeParser(newlineParser, predicateParser { isWhitespace(it) })

    fun <R> ignoreUnused(parser: Parser<R, Char>) = combineParserRight(repeatParser(whitespaceParser), parser)

    fun isIdentifierCharacter(c: Char) = !(isWhitespace(c) || c == '(' || c == ')' || c == '"')

    val identifierCharParser: Parser<Char, Char> = predicateParser(this::isIdentifierCharacter)
    val identifierParser: Parser<Expr.Identifier, Char> = ignoreUnused(mapParserResult(repeatParser1(identifierCharParser)) { e -> Expr.Identifier(e) })

    val quoteParser: Parser<Char, Char> = predicateParser { it == '"' }
    val stringCharParser: Parser<Char, Char> = predicateParser { it != '"' }
    val stringValueParser: Parser<Expr.String, Char> = mapParserResult(repeatParser(stringCharParser)) { e -> Expr.String(e) }
    val stringParser: Parser<Expr.String, Char> = ignoreUnused(combineParserRight(quoteParser, combineParserLeft(stringValueParser, quoteParser)))

    val openParenParser: Parser<Char, Char> = ignoreUnused(predicateParser { it == '(' })
    val closeParenParser: Parser<Char, Char> = ignoreUnused(predicateParser { it == ')' })

    val listExprParser1: Parser<Sequence<Expr>, Char> = combineParserRight(openParenParser, combineParserLeft(lazyParser { parser }, closeParenParser))
    val listExprParser: Parser<Expr.List, Char> = ignoreUnused(mapParserResult(listExprParser1) { e -> Expr.List(e) })
    val exprParser: Parser<Expr, Char> = alternativeParser(stringParser, alternativeParser(identifierParser, listExprParser))

    val parser: Parser<Sequence<Expr>, Char> = repeatParser(exprParser)
}