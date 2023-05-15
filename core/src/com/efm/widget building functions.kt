package com.efm

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.*
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.efm.assets.*
import com.efm.screens.MenuScreen

fun columnOf(vararg actors : Actor) : VerticalGroup
{
    val column = VerticalGroup()
    actors.forEach { column.addActor(it) }
    column.align(Align.center)
    column.space(10.0f)
    column.pack()
    return column
}

fun rowOf(vararg actors : Actor) : HorizontalGroup
{
    val row = HorizontalGroup()
    actors.forEach { row.addActor(it) }
    row.align(Align.center)
    row.space(10.0f)
    row.pack()
    return row
}

fun labelOf(
        text : String,
        fontType : BitmapFont,
        fontColor : Color,
        background : NinePatch
           ) : Label
{
    val labelStyle = Label.LabelStyle()
    labelStyle.font = fontType
    labelStyle.fontColor = fontColor
    labelStyle.background = NinePatchDrawable(background)
    val label = Label(text, labelStyle)
    label.pack()
    return label
}

fun textButtonOf(
        text : String,
        fontType : BitmapFont,
        fontColor : Color,
        up : NinePatch,
        down : NinePatch,
        over : NinePatch,
        disabled : NinePatch,
        focused : NinePatch,
        onClicked : () -> Unit
                ) : TextButton
{
    val textButtonStyle = TextButton.TextButtonStyle()
    textButtonStyle.font = fontType
    textButtonStyle.fontColor = fontColor
    textButtonStyle.up = NinePatchDrawable(up)
    textButtonStyle.down = NinePatchDrawable(down)
    textButtonStyle.over = NinePatchDrawable(over)
    textButtonStyle.disabled = NinePatchDrawable(disabled)
    textButtonStyle.focused = NinePatchDrawable(focused)
    val textButton = TextButton(text, textButtonStyle)
    textButton.pack()
    textButton.addListener(object : ClickListener()
                           {
                               override fun clicked(event : InputEvent?, x : Float, y : Float)
                               {
                                   onClicked()
                               }
                           })
    textButton.pad(10f)
    return textButton
}

fun imageButtonOf(
        image : Texture,
        up : NinePatch,
        down : NinePatch,
        over : NinePatch,
        disabled : NinePatch,
        focused : NinePatch,
        onClicked : () -> Unit
                 ) : ImageButton
{
    val imageButtonStyle = ImageButton.ImageButtonStyle()
    imageButtonStyle.imageUp = TextureRegionDrawable(image)
    imageButtonStyle.imageDown = TextureRegionDrawable(image)
    imageButtonStyle.imageOver = TextureRegionDrawable(image)
    imageButtonStyle.imageDisabled = TextureRegionDrawable(image)
    imageButtonStyle.up = NinePatchDrawable(up)
    imageButtonStyle.down = NinePatchDrawable(down)
    imageButtonStyle.over = NinePatchDrawable(over)
    imageButtonStyle.disabled = NinePatchDrawable(disabled)
    imageButtonStyle.focused = NinePatchDrawable(focused)
    val imageButton = ImageButton(imageButtonStyle)
    imageButton.addListener(object : ClickListener()
                            {
                                override fun clicked(event : InputEvent?, x : Float, y : Float)
                                {
                                    onClicked()
                                }
                            })
    imageButton.pad(10f)
    imageButton.pack()
    return imageButton
}

fun imageTextButtonOf(
        text : String,
        fontType : BitmapFont,
        fontColor : Color,
        image : Texture,
        up : NinePatch,
        down : NinePatch,
        over : NinePatch,
        disabled : NinePatch,
        focused : NinePatch,
        onClicked : () -> Unit
                     ) : ImageTextButton
{
    val imageTextButtonStyle = ImageTextButton.ImageTextButtonStyle()
    imageTextButtonStyle.font = fontType
    imageTextButtonStyle.fontColor = fontColor
    imageTextButtonStyle.imageUp = TextureRegionDrawable(image)
    imageTextButtonStyle.imageDown = TextureRegionDrawable(image)
    imageTextButtonStyle.imageOver = TextureRegionDrawable(image)
    imageTextButtonStyle.imageDisabled = TextureRegionDrawable(image)
    imageTextButtonStyle.up = NinePatchDrawable(up)
    imageTextButtonStyle.down = NinePatchDrawable(down)
    imageTextButtonStyle.over = NinePatchDrawable(over)
    imageTextButtonStyle.disabled = NinePatchDrawable(disabled)
    imageTextButtonStyle.focused = NinePatchDrawable(focused)
    val imageTextButton = ImageTextButton(text, imageTextButtonStyle)
    imageTextButton.addListener(object : ClickListener()
                                {
                                    override fun clicked(event : InputEvent?, x : Float, y : Float)
                                    {
                                        onClicked()
                                    }
                                })
    imageTextButton.pad(10f)
    imageTextButton.pack()
    return imageTextButton
}

fun imageOf(
        texture : Texture,
        scaling : Scaling
           ) : Image
{
    val image = Image(TextureRegionDrawable(texture), scaling, Align.center)
    image.pack()
    return image
}

fun checkBoxOf(
        text : String,
        fontType : BitmapFont,
        fontColor : Color,
        on : Texture,
        off : Texture,
        onOver : Texture,
        offOver : Texture,
        onDisabled : Texture,
        offDisabled : Texture
              ) : CheckBox
{
    val checkBoxStyle = CheckBox.CheckBoxStyle()
    checkBoxStyle.font = fontType
    checkBoxStyle.fontColor = fontColor
    checkBoxStyle.checkboxOn = TextureRegionDrawable(on)
    checkBoxStyle.checkboxOff = TextureRegionDrawable(off)
    checkBoxStyle.checkboxOnOver = TextureRegionDrawable(onOver)
    checkBoxStyle.checkboxOver = TextureRegionDrawable(offOver)
    checkBoxStyle.checkboxOnDisabled = TextureRegionDrawable(onDisabled)
    checkBoxStyle.checkboxOffDisabled = TextureRegionDrawable(offDisabled)
    val checkBox = CheckBox(text, checkBoxStyle)
    checkBox.pack()
    return checkBox
}

fun progressBarOf(
        min : Float,
        max : Float,
        step : Float,
        knobBackground : NinePatch,
        knobBefore : NinePatch,
        knobAfter : NinePatch
                 ) : ProgressBar
{
    val progressBarStyle = ProgressBar.ProgressBarStyle()
    progressBarStyle.background = NinePatchDrawable(knobBackground)
    progressBarStyle.knobBefore = NinePatchDrawable(knobBefore)
    progressBarStyle.knobAfter = NinePatchDrawable(knobAfter)
    val progressBar = ProgressBar(min, max, step, false, progressBarStyle)
    progressBar.value = 0.3f
    progressBar.pack()
    return progressBar
}

fun sliderOf(
        min : Float,
        max : Float,
        step : Float,
        knobBackground : NinePatch,
        knobBefore : NinePatch,
        knobAfter : NinePatch,
        knob : NinePatch
            ) : Slider
{
    val sliderStyle = Slider.SliderStyle()
    sliderStyle.background = NinePatchDrawable(knobBackground)
    sliderStyle.knobBefore = NinePatchDrawable(knobBefore)
    sliderStyle.knobAfter = NinePatchDrawable(knobAfter)
    sliderStyle.knob = NinePatchDrawable(knob)
    val slider = Slider(min, max, step, false, sliderStyle)
    slider.value = 0.3f
    slider.addListener(object : ChangeListener()
                       {
                           override fun changed(event : ChangeEvent?, actor : Actor)
                           {
                               //
                           }
                       })
    slider.pack()
    return slider
}

fun textFieldOf(
        text : String,
        fontType : BitmapFont,
        fontColor : Color,
        messageColor : Color,
        disabledColor : Color,
        background : NinePatch,
        disabledBackground : NinePatch,
        focusedBackground : NinePatch,
        cursor : NinePatch,
        selection : NinePatch
               ) : TextField
{
    val textFieldStyle = TextField.TextFieldStyle()
    textFieldStyle.font = fontType
    textFieldStyle.messageFont = fontType
    textFieldStyle.fontColor = fontColor
    textFieldStyle.messageFontColor = messageColor
    textFieldStyle.disabledFontColor = disabledColor
    textFieldStyle.background = NinePatchDrawable(background)
    textFieldStyle.disabledBackground = NinePatchDrawable(disabledBackground)
    textFieldStyle.focusedBackground = NinePatchDrawable(focusedBackground)
    textFieldStyle.cursor = NinePatchDrawable(cursor)
    textFieldStyle.selection = NinePatchDrawable(selection)
    val textField = TextField(text, textFieldStyle)
    textField.addListener(object : InputListener()
                          {
                              override fun keyTyped(event : InputEvent?, character : Char) : Boolean
                              {
                                  return super.keyTyped(event, character)
                              }
                          })
    return textField
}

fun textAreaOf(
        rows : Int,
        text : String,
        fontType : BitmapFont,
        fontColor : Color,
        messageColor : Color,
        disabledColor : Color,
        background : NinePatch,
        disabledBackground : NinePatch,
        focusedBackground : NinePatch,
        cursor : NinePatch,
        selection : NinePatch
              ) : TextArea
{
    val textFieldStyle = TextField.TextFieldStyle()
    textFieldStyle.font = fontType
    textFieldStyle.messageFont = fontType
    textFieldStyle.fontColor = fontColor
    textFieldStyle.messageFontColor = messageColor
    textFieldStyle.disabledFontColor = disabledColor
    textFieldStyle.background = NinePatchDrawable(background)
    textFieldStyle.disabledBackground = NinePatchDrawable(disabledBackground)
    textFieldStyle.focusedBackground = NinePatchDrawable(focusedBackground)
    textFieldStyle.cursor = NinePatchDrawable(cursor)
    textFieldStyle.selection = NinePatchDrawable(selection)
    val textArea = TextArea(text, textFieldStyle)
    textArea.addListener(object : InputListener()
                         {
                             override fun keyTyped(event : InputEvent?, character : Char) : Boolean
                             {
                                 return super.keyTyped(event, character)
                             }
                         })
    textArea.setPrefRows(rows.toFloat())
    textArea.pack()
    return textArea
}

fun windowAreaOf(
        title: String,
        rows: Int,
        fontType: BitmapFont,
        fontColor: Color,
        messageColor: Color,
        disabledColor: Color,
        background: NinePatch,
        disabledBackground: NinePatch,
        focusedBackground: NinePatch,
        cursor: NinePatch,
        selection: NinePatch
                    ): Window {
    val windowStyle = Window.WindowStyle()
    windowStyle.titleFont = fontType
    windowStyle.titleFontColor = fontColor
    windowStyle.background = NinePatchDrawable(background)
    
    val window = Window(title, windowStyle)
    
    val yesButton = imageButtonOf(
            Textures.check,
            Textures.upNinePatch,
            Textures.downNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                 )
    {
        playSoundOnce(Sounds.blop)
    }
    
    val noButton = imageButtonOf(
            Textures.close,
            Textures.upNinePatch,
            Textures.downNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                 )
    {
        playSoundOnce(Sounds.blop)
    }
    
    window.add(yesButton).padTop(50f).padBottom(50f)
    window.add(noButton).padTop(50f).padBottom(50f)
    
    return window
}

fun menuPopup(
        title: String,
        fontType: BitmapFont,
        fontColor: Color,
        background: NinePatch
                ): Window {
    val windowStyle = Window.WindowStyle()
    windowStyle.titleFont = fontType
    windowStyle.titleFontColor = fontColor
    windowStyle.background = NinePatchDrawable(background)
    
    val window = Window(title, windowStyle)
    
    // Get the title label from the window's title table
    val titleLabel = window.titleTable.getCell(window.titleLabel).actor as Label
    
    // Set the alignment of the title label to center
    titleLabel.setAlignment(Align.center)
    
    // Set the width of the title label to fill the title table
    window.titleTable.getCell(titleLabel).width(Value.percentWidth(1f, window.titleTable)).padTop(50f)

    
    val resumeButton = textButtonOf(
            "resume",
            Fonts.inconsolata30,
            Colors.darkGray,
            Textures.upLongNinePatch,
            Textures.downLongNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                        )
    {
        window.isVisible = false
        Sounds.blop.playOnce()
    }
    
    val equipmentButton = textButtonOf(
            "equipment",
            Fonts.inconsolata30,
            Colors.darkGray,
            Textures.upLongNinePatch,
            Textures.downLongNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                      ){
        Sounds.blop.playOnce()
    }
    
    val settingsButton = textButtonOf(
            "settings",
            Fonts.inconsolata30,
            Colors.darkGray,
            Textures.upLongNinePatch,
            Textures.downLongNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                   )
    {
        Sounds.blop.playOnce()
    }
    
    val backToMenuButton = textButtonOf(
            "back to menu",
            Fonts.inconsolata30,
            Colors.darkGray,
            Textures.upLongNinePatch,
            Textures.downLongNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                   )
    {
        Sounds.blop.playOnce()
        changeScreen(MenuScreen)
    }
    
    window.add(columnOf(resumeButton, equipmentButton, settingsButton, backToMenuButton)).pad(50f)
    
    return window
}