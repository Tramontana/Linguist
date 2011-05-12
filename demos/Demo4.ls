!	Demo4.ls

!	Makes the buttons prettier and adds a cursor.  Note: Custom cursors are
!	available in Java 2 but I haven't yet implemented them.  The same goes
!	for installed fonts.

	package basic
	package graphics

	window MyWindow
	label Background
	button ShowButton
	button ExitButton
	border Border
	cursor HandCursor

	image BACKGROUND from "Family.jpg"

	! Create the window with a plain background
	create MyWindow
		title "Another example"
		at 100 100
		size 570 373
	on close MyWindow exit
	show MyWindow

!	Now add a label to act as a background.
	create Background in MyWindow
	set the background color of Background to 236 190 202
	show Background

	! Create a border for use with the buttons.
	create Border style bevel raised

	! Create the hand cursor.
	create HandCursor hand

	! Create the Show button that will change the background when clicked.
	create ShowButton in MyWindow text "Show" at 40 40 size 100 40
	set the border of ShowButton to Border
	set the cursor of ShowButton to HandCursor
	on ShowButton go to ShowButtonCB
	show ShowButton

	! Create the Exit button but don't show it yet.
	! See what happens if you don't make it opaque.
	create ExitButton in MyWindow text "Exit" at 420 40 size 100 40
	set the border of ExitButton to Border
	set the cursor of ExitButton to HandCursor
!	set the style of ExitButton to opaque
	on ExitButton exit
	stop

!	When the user clicks Show the background is changed, the button is hidden
!	and the Exit button is revealed.
ShowButtonCB:
	set the icon of Background to BACKGROUND
	set the title of MyWindow to "This is some of the family"
	hide ShowButton
	show ExitButton
	stop
