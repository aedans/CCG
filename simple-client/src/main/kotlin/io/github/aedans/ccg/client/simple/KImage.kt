package io.github.aedans.ccg.client.simple

import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JLabel

open class KImage(image: BufferedImage) : JLabel(ImageIcon(image))
