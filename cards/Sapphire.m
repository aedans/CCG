(def name "Sapphire")
(def text "Grants [Sapphire].")
(def type (starting artifact))
(def cost (list (mana 1)))
(def cast cast-permanent)
(def enters-field '(add-gem caster sapphire 1))
(def leaves-field '(add-gem caster sapphire -1))
