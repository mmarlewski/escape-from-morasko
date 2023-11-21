package com.efm

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.*
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import com.efm.assets.*
import com.efm.level.World
import com.efm.screens.MenuScreen
import com.efm.skill.BodyPart
import com.efm.skill.Skill
import com.efm.ui.gameScreen.*

lateinit var musicSlider : Slider
lateinit var soundSlider : Slider

fun highlightSelection(imageButton : ImageButton, down : NinePatch, up : NinePatch)
{
    imageButton.addListener(object : ChangeListener()
                            {
                                override fun changed(event : ChangeEvent?, actor : Actor?)
                                {
                                    if (imageButton.isChecked)
                                    {
                                        imageButton.style.up = NinePatchDrawable(down)
                                    }
                                    else
                                    {
                                        imageButton.style.up = NinePatchDrawable(up)
                                    }
                                }
                            })
}

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
        onYes : () -> Unit,
        onNo : () -> Unit
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
        Sounds.ui_2.playOnce()
        window.isVisible = false
        onYes()
        PopUps.setBackgroundVisibility(true)
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
        Sounds.ui_3.playOnce()
        onNo()
        PopUps.setBackgroundVisibility(true)
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
    val soundEffectsLabel = labelOf("sound", Fonts.pixeloid20, Colors.black, Textures.translucentNinePatch)
    
    musicSlider = sliderOf(
            0.0f,
            1.0f,
            0.1f,
            1.0f,
            Textures.materialKnobNinePatchBeforeBlack,
            Textures.materialKnobNinePatchAfter,
            Textures.materialKnobNinePatchBeforeBlack,
            Textures.materialKnobNinePatch
                          )
    
    soundSlider = sliderOf(
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
        Sounds.ui_1.playOnce()
        PopUps.setMenuVisibility(true)
        LeftStructure.menuButton.isVisible = true
    }
    
    musicSlider.addListener(object : ChangeListener()
                            {
                                override fun changed(event : ChangeEvent, actor : Actor)
                                {
                                    setMusicVolume(musicSlider.value)
                                }
                            })
    
    soundSlider.addListener(object : ChangeListener()
                            {
                                override fun changed(event : ChangeEvent, actor : Actor)
                                {
                                    setSoundVolume(soundSlider.value)
                                }
                            })
    
    val buttonTable = Table()
    buttonTable.add(
            columnOf(
                    rowOf(musicLabel, musicSlider),
                    rowOf(soundEffectsLabel, soundSlider),
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
        Sounds.ui_3.playOnce()
        PopUps.setBackgroundVisibility(true)
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
                                      )
    {
        window.isVisible = false
        EquipmentStructure.showHeroEquipment()
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
        Sounds.ui_1.playOnce()
        window.isVisible = false
        PopUps.setSettingsVisibility(true)
        LeftStructure.menuButton.isVisible = false
        musicSlider.value = getMusicVolume()
        soundSlider.value = getSoundVolume()
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
        Sounds.ui_1.playOnce()
        changeScreen(MenuScreen)
        window.isVisible = false
        PopUps.setBackgroundVisibility(true)
    }
    
    window.add(columnOf(resumeButton, equipmentButton, settingsButton, backToMenuButton)).pad(50f)
    
    return window
}

val itemButtonWithHealthBarGroup = ButtonGroup<ImageButton>()

fun itemButtonWithHealthBar(
        image : Texture?,
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
    itemButtonWithHealthBarGroup.add(imageButton)
    
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
            maxHealth.toFloat(),
            1f,
            currentHealth.toFloat(),
            Textures.translucentNinePatch,
            Textures.knobWhiteNinePatch,
            Textures.translucentNinePatch,
            4f
                                 )
    
    val healthPercentage = currentHealth.toFloat() / maxHealth.toFloat()
    val color = when
    {
        healthPercentage == 0f    -> Colors.black
        healthPercentage <= 0.25f -> Colors.red
        healthPercentage <= 0.5f  -> Colors.orange
        healthPercentage <= 0.75f -> Colors.yellow
        else                      -> Colors.green
    }
    healthBar.color = color
    
    val barWidth = 58f
    val barStack = Stack()
    val barContainer : Container<ProgressBar> = Container(healthBar).padLeft(4f)
    barContainer.width(barWidth)
    barStack.add(barContainer)
    
    val table = Table()
    table.add(barStack).padTop(48f).padLeft(-48f)

//    highlightSelection(imageButton, down, up)
    
    imageButton.add(table)
    imageButton.padLeft(24f)
    
    return imageButton
}

val itemButtonWithLabelGroup = ButtonGroup<ImageButton>()
fun itemButtonWithLabel(
        image : Texture?,
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
    
    var image = if (image != null) imageOf(image, Scaling.none) else null
    
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
    itemButtonWithLabelGroup.add(imageButton)
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

fun buttonWithTextOverlay(
        image : Texture,
        text : String,
        up : NinePatch,
        down : NinePatch,
        over : NinePatch,
        disabled : NinePatch,
        focused : NinePatch,
        onClicked : () -> Unit
                         ) : Stack
{
    val backgroundButton = imageButtonOf(image, up, down, over, disabled, focused, onClicked)
    val background = imageOf(Textures.translucentThreeQuartersBlack, Scaling.none)
    val foreground = labelOf(text, Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
    
    val table = Table()
    table.add(foreground).expand().center()
    
    val stack = Stack()
    stack.add(backgroundButton)
    stack.add(background)
    stack.add(table)
    table.addListener(object : ClickListener()
                      {})
    
    return stack
}

fun equipmentOverlay(
        title : String
                    ) : Table
{
    
    val table = Table()
    table.background = NinePatchDrawable(Textures.pauseBackgroundNinePatch)
    
    val titleLabel = labelOf(title, Fonts.pixeloid30, Colors.black, Textures.translucentNinePatch)
    table.add(titleLabel).align(Align.center).row()
    
    for (i in 0 until EQUIPMENT_ROWS)
    {
        val itemRow = rowOf()
        table.add(itemRow).align(Align.center).row()
    }
    
    return table
}

const val EQUIPMENT_ROW_MAX = 5
const val EQUIPMENT_ROWS = 5

fun determineBodyPart(skill : Skill) : Texture
{
    return when (skill.bodyPart)
    {
        BodyPart.head      -> Textures.skillHead
        BodyPart.leftHand  -> Textures.skillArmLeft
        BodyPart.rightHand -> Textures.skillArmRight
        BodyPart.torso     -> Textures.skillTorso
        BodyPart.leftLeg   -> Textures.skillLegLeft
        BodyPart.rightLeg  -> Textures.skillLegRight
    }
}

fun skillAssignDisplay(skill : Skill, onClicked : (Skill) -> Unit) : Table
{
    val bodyPart = determineBodyPart(skill)
    val skillIcon = imageOf(skill.texture, Scaling.none)
    val bodyPartIcon = imageOf(bodyPart, Scaling.none)
    val skillName = labelOf(skill.name, Fonts.pixeloid20, Colors.darkGray, Textures.translucentNinePatch)
    val skillDescription = labelOf(skill.description, Fonts.pixeloid20, Colors.black, Textures.translucentNinePatch)
    val assignButton = textButtonOf(
            "Assign", Fonts.pixeloid20, Colors.black,
            Textures.upNinePatch,
            Textures.downNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch,
                                   )
    {
        onClicked(skill)
        PopUps.setSkillAssignmentVisibility(false)
    }
    
    skillDescription.setFontScale(0.6f)
    skillName.setFontScale(0.8f)
    
    skillDescription.setWrap(true)
    skillDescription.setAlignment(Align.center)
    
    val table = Table()
    table.add(skillIcon).padTop(64f).row()
    table.add(bodyPartIcon).padTop(8f).row()
    table.add(skillName).row()
    table.add(skillDescription).width(160f).height(80f).row()
    table.add(assignButton).width(160f).padBottom(24f).row()
    
    return table
}

fun skillReassignDisplay(skill : Skill, onClicked : (Skill) -> Unit) : Table
{
    val bodyPart = determineBodyPart(skill)
    val skillIcon = imageOf(skill.texture, Scaling.none)
    val bodyPartIcon = imageOf(bodyPart, Scaling.none)
    val skillName = labelOf(skill.name, Fonts.pixeloid20, Colors.darkGray, Textures.translucentNinePatch)
    val skillDescription = labelOf(skill.description, Fonts.pixeloid20, Colors.black, Textures.translucentNinePatch)
    val reassignButton = textButtonOf(
            "Reassign", Fonts.pixeloid20, Colors.black,
            Textures.upNinePatch,
            Textures.downNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch,
                                     )
    {
        onClicked(skill)
        PopUps.setSkillAssignmentVisibility(false)
    }
    val reassignmentInfo = labelOf("Skill already assigned", Fonts.pixeloid10, Colors.red, Textures.translucentNinePatch)
    
    skillDescription.setFontScale(0.6f)
    skillName.setFontScale(0.8f)
    
    
    skillDescription.setWrap(true)
    skillDescription.setAlignment(Align.center)
    
    val table = Table()
    table.add(skillIcon).padTop(64f).row()
    table.add(bodyPartIcon).padTop(8f).row()
    table.add(skillName).row()
    table.add(skillDescription).width(160f).height(80f).row()
    table.add(reassignButton).width(160f).row()
    table.add(reassignmentInfo).padBottom(24f - reassignmentInfo.height).row()
    
    return table
}

fun isAnySkillAssigned(skill : Skill) : Boolean
{
    return World.hero.bodyPartMap[skill.bodyPart] != null
}

fun skillsAssignmentOverlay(
        skillLeft : Skill,
        skillMiddle : Skill,
        skillRight : Skill,
        onAssign : (Skill) -> Unit,
        onReassign : (Skill) -> Unit
                           ) : Window
{
    val windowStyle = Window.WindowStyle()
    windowStyle.titleFont = Fonts.pixeloid30
    windowStyle.titleFontColor = Colors.black
    windowStyle.background = NinePatchDrawable(Textures.pauseBackgroundNinePatch)
    
    val window = Window("Congratulations! You defeated a boss", windowStyle)
    val titleLabel = window.titleTable.getCell(window.titleLabel).actor as Label
    titleLabel.setAlignment(Align.center)
    window.titleTable.getCell(titleLabel).width(Value.percentWidth(1f, window.titleTable)).padTop(75f)
    val skillLeftToDisplay = if (isAnySkillAssigned(skillLeft))
    {
        skillReassignDisplay(skillLeft, onAssign).padLeft(64f)
    }
    else
    {
        skillAssignDisplay(skillLeft, onReassign).padLeft(64f)
    }
    
    val skillMiddleToDisplay = if (isAnySkillAssigned(skillMiddle))
    {
        skillReassignDisplay(skillMiddle, onAssign).padLeft(96f)
    }
    else
    {
        skillAssignDisplay(skillMiddle, onReassign).padLeft(96f)
    }
    
    val skillRightToDisplay = if (isAnySkillAssigned(skillRight))
    {
        skillReassignDisplay(skillRight, onAssign).padLeft(96f).padRight(64f)
    }
    else
    {
        skillAssignDisplay(skillRight, onReassign).padLeft(96f).padRight(64f)
    }
    
    window.add(rowOf(skillLeftToDisplay, skillMiddleToDisplay, skillRightToDisplay))
    return window
}

fun coloredRectangle(color : Color, width : Float, height : Float) : Actor
{
    return object : Actor()
    {
        private val shapeRenderer = ShapeRenderer()
        
        init
        {
            setSize(width, height)
            shapeRenderer.color = color
        }
        
        override fun draw(batch : Batch?, parentAlpha : Float)
        {
            super.draw(batch, parentAlpha)
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.rect(x, y, width, height)
            shapeRenderer.end()
        }
    }
}

fun tutorialPopup(
        title : String,
        body : String,
        onOK : () -> Unit
                 ) : Window
{
    val windowStyle = Window.WindowStyle()
    windowStyle.titleFont = Fonts.pixeloid30
    windowStyle.titleFontColor = Colors.white
    windowStyle.background = NinePatchDrawable(Textures.pauseBackgroundBlackNinePatch)
    
    val window = Window(title, windowStyle)
    val titleLabel = window.titleTable.getCell(window.titleLabel).actor as Label
    titleLabel.setAlignment(Align.center)
    window.titleTable.getCell(titleLabel).width(Value.percentWidth(1f, window.titleTable)).padTop(48f)
    
    val titleWidth = window.titleLabel.width
    
    val delimiter = labelOf("", Fonts.pixeloid10, Colors.white, Textures.pauseBackgroundWhiteNinePatch)
    delimiter.setFontScale(0.1f)
    
    val description = labelOf(body, Fonts.pixeloid20, Colors.white, Textures.translucentNinePatch)
    description.setWrap(true)
    description.setAlignment(Align.center)
    
    val okButton = textButtonOf(
            "OK",
            Fonts.pixeloid20,
            Colors.black,
            Textures.upNinePatch,
            Textures.downNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch,
            onOK
                               )
    
    val table = Table()
    table.add(delimiter).fillX().height(1f).padTop(40f).row()
    table.add(description).width(titleWidth).height(44f).padTop(16f).row()
    table.add(okButton).width(180f).padTop(16f).padBottom(8f).row()
    
    
    
    window.add(columnOf(table))
    
    return window
}