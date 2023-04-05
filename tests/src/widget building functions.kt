import com.badlogic.gdx.utils.Scaling
import com.efm.*
import com.efm.assets.*
import org.junit.*
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `widget building functions`
{
    @Test fun `columnOf with no arguments`()
    {
        val column = columnOf()
        
        assertTrue(column.children.isEmpty)
    }
    
    @Test fun `columnOf with one arguments`()
    {
        val label = labelOf("label", Fonts.freezing32, Colors.blue, Textures.upNinePatch)
        val column = columnOf(label)
        
        assertTrue(label in column.children)
    }
    
    @Test fun `columnOf with a few arguments`()
    {
        val label1 = labelOf("label", Fonts.freezing32, Colors.blue, Textures.upNinePatch)
        val label2 = labelOf("label", Fonts.freezing32, Colors.blue, Textures.upNinePatch)
        val column = columnOf(label1, label2)
        
        assertTrue(label1 in column.children && label2 in column.children)
    }
    
    @Test fun `rowOf with no arguments`()
    {
        val row = rowOf()
        
        assertTrue(row.children.isEmpty)
    }
    
    @Test fun `rowOf with one arguments`()
    {
        val label = labelOf("label", Fonts.freezing32, Colors.blue, Textures.upNinePatch)
        val row = rowOf(label)
        
        assertTrue(label in row.children)
    }
    
    @Test fun `rowOf with a few arguments`()
    {
        val label1 = labelOf("label", Fonts.freezing32, Colors.blue, Textures.upNinePatch)
        val label2 = labelOf("label", Fonts.freezing32, Colors.blue, Textures.upNinePatch)
        val row = rowOf(label1, label2)
        
        assertTrue(label1 in row.children && label2 in row.children)
    }
    
    @Test fun `labelOf initializes all members of Label`()
    {
        val label = labelOf("label", Fonts.freezing32, Colors.blue, Textures.upNinePatch)
        
        label.text
        label.color
        label.style.font
        label.style.fontColor
        label.style.background
    }
    
    @Test fun `textButtonOf initializes all members of TextButton`()
    {
        val textButton = textButtonOf(
                "text button",
                Fonts.freezing32,
                Colors.blue,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                     ) {}
        
        textButton.text
        textButton.color
        textButton.style.font
        textButton.style.fontColor
        textButton.style.up
        textButton.style.down
        textButton.style.over
        textButton.style.disabled
        textButton.style.focused
    }
    
    @Test fun `imageButtonOf initializes all members of ImageButton`()
    {
        val imageButton = imageButtonOf(
                Textures.coin,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                       ) {}
        
        imageButton.image
        imageButton.color
        imageButton.style.up
        imageButton.style.down
        imageButton.style.over
        imageButton.style.disabled
        imageButton.style.focused
    }
    
    @Test fun `imageTextButtonOf initializes all members of ImageTextButton`()
    {
        val imageTextButton = imageTextButtonOf(
                "image text button",
                Fonts.freezing32,
                Colors.blue,
                Textures.coin,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                               ) {}
        
        imageTextButton.text
        imageTextButton.color
        imageTextButton.image
        imageTextButton.style.font
        imageTextButton.style.fontColor
        imageTextButton.style.up
        imageTextButton.style.down
        imageTextButton.style.over
        imageTextButton.style.disabled
        imageTextButton.style.focused
    }
    
    @Test fun `imageOf initializes all members of Image`()
    {
        val image = imageOf(Textures.coin, Scaling.fill)
        
        image.color
    }
    
    @Test fun `checkBoxOf initializes all members of CheckBox`()
    {
        val checkBox = checkBoxOf(
                "check box",
                Fonts.freezing32,
                Colors.blue,
                Textures.checkBoxOn,
                Textures.checkBoxOff,
                Textures.checkBoxOnOver,
                Textures.checkBoxOffOver,
                Textures.checkBoxOnDisabled,
                Textures.checkBoxOffDisabled
                                 )
        
        checkBox.text
        checkBox.color
        checkBox.image
        checkBox.background
        checkBox.style.checkboxOn
        checkBox.style.checkboxOff
        checkBox.style.checkboxOnOver
        checkBox.style.checkboxOver
        checkBox.style.checkboxOnDisabled
        checkBox.style.checkboxOffDisabled
    }
    
    @Test fun `progressBarOf initializes all members of ProgressBar`()
    {
        val progressBar = progressBarOf(
                0f, 1f, 0.1f,
                Textures.knobBackgroundNinePatch,
                Textures.knobBeforeNinePatch,
                Textures.knobAfterNinePatch
                                       )
        
        progressBar.color
        progressBar.style.knob
        progressBar.style.disabledKnob
        progressBar.style.knobBefore
        progressBar.style.disabledKnobBefore
        progressBar.style.knobAfter
        progressBar.style.disabledKnobAfter
        progressBar.style.background
        progressBar.style.disabledBackground
    }
    
    @Test fun `sliderOf initializes all members of Slider`()
    {
        val slider = sliderOf(
                0f, 1f, 0.1f,
                Textures.knobBackgroundNinePatch,
                Textures.knobBeforeNinePatch,
                Textures.knobAfterNinePatch,
                Textures.knobNinePatch
                             )
        
        slider.color
        slider.style.knob
        slider.style.disabledKnob
        slider.style.knobBefore
        slider.style.disabledKnobBefore
        slider.style.knobAfter
        slider.style.disabledKnobAfter
        slider.style.background
        slider.style.disabledBackground
    }
    
    @Test fun `textFieldOf initializes all members of TextField`()
    {
        val textField = textFieldOf(
                "slider",
                Fonts.freezing32,
                Colors.blue,
                Colors.blue,
                Colors.blue,
                Textures.upNinePatch,
                Textures.upNinePatch,
                Textures.upNinePatch,
                Textures.cursorNinePatch,
                Textures.selectionNinePatch
                                   )
        
        textField.text
        textField.color
        textField.style.font
        textField.style.fontColor
        textField.style.messageFont
        textField.style.messageFontColor
        textField.style.focusedFontColor
        textField.style.messageFont
        textField.style.disabledFontColor
        textField.style.background
        textField.style.disabledBackground
        textField.style.focusedBackground
        textField.style.cursor
        textField.style.selection
    }
    
    @Test fun `textAreaOf initializes all members of TextArea`()
    {
        val textArea = textAreaOf(
                1, "slider",
                Fonts.freezing32,
                Colors.blue,
                Colors.blue,
                Colors.blue,
                Textures.upNinePatch,
                Textures.upNinePatch,
                Textures.upNinePatch,
                Textures.cursorNinePatch,
                Textures.selectionNinePatch
                                 )
        
        textArea.text
        textArea.color
        textArea.style.font
        textArea.style.fontColor
        textArea.style.messageFont
        textArea.style.messageFontColor
        textArea.style.focusedFontColor
        textArea.style.messageFont
        textArea.style.disabledFontColor
        textArea.style.background
        textArea.style.disabledBackground
        textArea.style.focusedBackground
        textArea.style.cursor
        textArea.style.selection
    }
}
