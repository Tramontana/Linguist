!	Demo3.ls

!	Adds a couple of buttons.

	package basic
	package graphics

	window MyWindow
	label Background
	button ShowButton
	button ExitButton

	image BACKGROUND from "Family.jpg"

	! Create the window with a plain background
	create MyWindow
		title "This is some of the family"
		at 100 100
		size 570 373
	on close MyWindow exit
	show MyWindow

!	Now add a label to act as a background.
	create Background in MyWindow
	set the background color of Background to 236 190 202
	show Background

	! Create the Show button that will change the background when clicked.
	create ShowButton in MyWindow text "Show" at 40 40 size 100 40
	on ShowButton go to ShowButtonCB
	show ShowButton

	!	Create the Exit button but don't show it yet.  Make it opaque.
	create ExitButton in MyWindow text "Exit" at 420 40 size 100 40
	set the style of ExitButton to opaque
	on ExitButton exit
	stop

!	When the user clicks Show the background is changed, the button is hidden
!	and the Exit button is revealed.
ShowButtonCB:
	set the icon of Background to BACKGROUND
	hide ShowButton
	show ExitButton
	stop
