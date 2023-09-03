package io.writerme.app.ui.component

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.PathParser
import java.util.regex.Pattern

class HeartShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val pathData ="""M60.83,17.19C68.84,8.84,74.45,1.62,86.79,0.21c23.17-2.66,44.48,21.06,32.78,44.41 c-3.33,6.65-10.11,14.56-17.61,22.32c-8.23,8.52-17.34,16.87-23.72,23.2l-17.4,17.26L46.46,93.56C29.16,76.9,0.95,55.93,0.02,29.95 C-0.63,11.75,13.73,0.09,30.25,0.3C45.01,0.5,51.22,7.84,60.83,17.19L60.83,17.19L60.83,17.19z"""
        val scaleX = size.width/122.88F
        val scaleY = size.height/107.41F
        return Outline.Generic(PathParser.createPathFromPathData(resize(pathData,scaleX, scaleY)).asComposePath())
    }

    private fun resize(pathData: String,scaleX:Float,scaleY:Float): String {
        val matcher = Pattern.compile("[0-9]+[.]?([0-9]+)?").matcher(pathData) // match the numbers in the path
        val stringBuffer = StringBuffer()
        var count = 0
        while(matcher.find()){
            val number = matcher.group().toFloat()
            matcher.appendReplacement(stringBuffer,(if(count % 2 == 0) number * scaleX else number * scaleY).toString()) // replace numbers with scaled numbers
            ++count
        }
        return stringBuffer.toString() // return the scaled path
    }
}