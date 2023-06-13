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
import com.efm.ui.gameScreen.PopUps

fun columnOf(vararg actors : Actor) : VerticalGroup
{
    val column = VerticalGroup()
    actors.forEach { column.addActor(it) }
    column.align(Align.center)
    column.pad(10.0f)
    column.space(10.0f)
    return column
}

fun rowOf(vararg actors : Actor) : HorizontalGroup
{
    val row = HorizontalGroup()
    actors.forEach { row.addActor(it) }
    row.align(Align.center)
    row.pad(10.0f)
    row.space(10.0f)
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
    return imageTextButton
}

fun imageOf(
        texture : Texture,
        scaling : Scaling
           ) : Image
{
    val image = Image(TextureRegionDrawable(texture), scaling, Align.center)
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
    return checkBox
}

fun progressBarOf(
        min : Float,
        max : Float,
        step : Float,
        start : Float,
        knobBackground : NinePatch,
        knobBefore : NinePatch,
        knobAfter : NinePatch,
        height : Float
                 ) : ProgressBar
{
    val progressBarStyle = ProgressBar.ProgressBarStyle()
    
    // Create a custom background drawable with the desired width and height
    val backgroundDrawable = NinePatchDrawable(knobBackground)
    backgroundDrawable.minHeight = height
    
    // Create a custom knobBefore drawable with the desired width and height
    val knobBeforeDrawable = NinePatchDrawable(knobBefore)
    knobBeforeDrawable.minHeight = height
    
    // Create a custom knobAfter drawable with the desired width and height
    val knobAfterDrawable = NinePatchDrawable(knobAfter)
    knobAfterDrawable.minHeight = height
    
    progressBarStyle.background = backgroundDrawable
    progressBarStyle.knobBefore = knobBeforeDrawable
    progressBarStyle.knobAfter = knobAfterDrawable
    
    val progressBar = ProgressBar(min, max, step, false, progressBarStyle)
    progressBar.value = start
    
    return progressBar
}

fun sliderOf(
        min : Float,
        max : Float,
        step : Float,
        start : Float,
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
    slider.value = start
    slider.addListener(object : ChangeListener()
                       {
                           override fun changed(event : ChangeEvent?, actor : Actor)
                           {
                               //
                           }
                       })
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
    return textArea
}

fun windowAreaOf(
        title : String,
        fontType : BitmapFont,
        fontColor : Color,
        background : NinePatch,
//        onYes: () -> Unit
                ) : Window
{
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
    window.titleTable.getCell(titleLabel).width(Value.percentWidth(1f, window.titleTable)).padTop(75f)
    
    val yesButton = imageButtonOf(
            Textures.check,
            Textures.upNinePatch,
            Textures.downNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                 )
    {
        Sounds.blop.playOnce()
//        window.isVisible = false
//        onYes()
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
        window.isVisible = false
        Sounds.blop.playOnce()
    }
    
    val buttonTable = Table()
    buttonTable.add(yesButton)
    buttonTable.add().width(64f) // Add an empty cell with a preferred width
    buttonTable.add(noButton)
    
    window.add(buttonTable).padTop(100f).padLeft(100f).padRight(100f).padBottom(50f)
    
    return window
}

fun settingsPause(
        title : String,
        fontType : BitmapFont,
        fontColor : Color,
        background : NinePatch,
                 ) : Window
{
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
    window.titleTable.getCell(titleLabel).width(Value.percentWidth(1f, window.titleTable)).padTop(75f)
    
    val musicLabel = labelOf("music", Fonts.pixeloid20, Colors.black, Textures.translucentNinePatch)
    val soundEffectsLabel = labelOf("sound effects", Fonts.pixeloid20, Colors.black, Textures.translucentNinePatch)
    
    val musicRadioButton = checkBoxOf(
            "", Fonts.pixeloid10, Colors.black,
            Textures.materialCheckboxOn,
            Textures.materialCheckboxOff,
            Textures.materialCheckboxOn,
            Textures.materialCheckboxOff,
            Textures.materialCheckboxOn,
            Textures.materialCheckboxOff
                                     )
    val musicSlider = sliderOf(
            0.0f,
            1.0f,
            0.1f,
            1.0f,
            Textures.materialKnobNinePatchBeforeBlack,
            Textures.materialKnobNinePatchAfter,
            Textures.materialKnobNinePatchBeforeBlack,
            Textures.materialKnobNinePatch
                              )
    
    val soundEffectsRadioButton = checkBoxOf(
            "", Fonts.pixeloid10, Colors.black,
            Textures.materialCheckboxOn,
            Textures.materialCheckboxOff,
            Textures.materialCheckboxOn,
            Textures.materialCheckboxOff,
            Textures.materialCheckboxOn,
            Textures.materialCheckboxOff
                                            )
    val soundEffectsmusicSlider = sliderOf(
            0.0f,
            1.0f,
            0.1f,
            1.0f,
            Textures.materialKnobNinePatchBeforeBlack,
            Textures.materialKnobNinePatchAfter,
            Textures.materialKnobNinePatchBeforeBlack,
            Textures.materialKnobNinePatch
                                          )
    
    val backButton = textButtonOf(
            "back",
            Fonts.inconsolata30,
            Colors.black,
            Textures.upLongNinePatch,
            Textures.downLongNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                 )
    {
        window.isVisible = false
        Sounds.blop.playOnce()
        PopUps.setMenuVisibility(true)
    }
    
    val buttonTable = Table()
    buttonTable.add(
            columnOf(
                    rowOf(
                            musicLabel,
                            musicRadioButton,
                            musicSlider
                         ),
                    rowOf(soundEffectsLabel, soundEffectsRadioButton, soundEffectsmusicSlider, columnOf().padLeft(75f)),
                    rowOf(backButton)
                    )
                   )
    
    window.add(buttonTable).padTop(100f).padLeft(100f).padRight(100f).padBottom(50f)
    
    return window
}

fun menuPopup(
        title : String,
        fontType : BitmapFont,
        fontColor : Color,
        background : NinePatch
             ) : Window
{
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
                                      ) {
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
        window.isVisible = false
        PopUps.setSettingsVisibility(true)
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
        window.isVisible = false
    }
    
    window.add(columnOf(resumeButton, equipmentButton, settingsButton, backToMenuButton)).pad(50f)
    
    return window
}

fun itemButtonWithHealthbar(
        image : Texture,
        maxHealth : Int,
        currentHealth : Int,
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
    
    var healthBar = progressBarOf(
            0f,
            currentHealth.toFloat(),
            1f,
            maxHealth.toFloat(),
            Textures.translucentNinePatch,
            Textures.knobHealthbarAfterNinePatch,
            Textures.translucentNinePatch,
            4f
                                 )

//    //green
//    healthBar.setColor(36f,222f,66f,100f)
//    //yellow
//    healthBar.setColor(218f, 222f, 36f, 100f)
//    //orange
//    healthBar.setColor(222f, 148f, 36f, 100f)
    //red
    healthBar.setColor(222f, 58f, 36f, 100f)
    
    val barWidth = 64f
    val barStack = Stack()
    val barContainer : Container<ProgressBar> = Container(healthBar)
    barContainer.width(barWidth)
    barStack.add(barContainer)
    
    val table = Table()
    table.add(barStack).padTop(48f).padLeft(-48f)
    
    imageButton.add(table)
    imageButton.padLeft(24f)
    
    return imageButton
}

fun itemButtonWithLabel(
        image : Texture,
        text : String,
        up : NinePatch,
        down : NinePatch,
        over : NinePatch,
        disabled : NinePatch,
        focused : NinePatch,
        onClicked : () -> Unit
                       ) : ImageButton
{
    val imageButtonTmp = imageButtonOf(Textures.translucent1px, up, down, over, disabled, focused, onClicked)
    
    var image = imageOf(image, Scaling.none)
    
    var label = labelOf(
            text,
            Fonts.pixeloid10,
            Color.BLACK,
            Textures.translucentNinePatch
                       )
    val table = Table()
    table.add(image).padLeft(16f)
    table.add(label).padTop(32f).padLeft(-8f)
    
    val stack = Stack()
    stack.add(table)
    
    val imageButton = ImageButton(imageButtonTmp.style)
    imageButton.addListener(object : ClickListener()
                            {
                                override fun clicked(event : InputEvent?, x : Float, y : Float)
                                {
                                    onClicked()
                                }
                            })
    
    imageButton.addListener(object : ClickListener()
                            {
                                override fun clicked(event : InputEvent?, x : Float, y : Float)
                                {
                                    onClicked()
                                }
                            })
    imageButton.add(stack)
    return imageButton
}
