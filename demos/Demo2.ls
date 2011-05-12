!	Demo2.ls

!	Opens a window, uses a graphic for the background and waits for the close box.

	package basic
	package graphics

	window MyWindow
	label Background

	! The image (a family pic taken a few summers back; yes, the bearded one is me.
	! Since then I just got greyer.)
	! The image is compiled into the output file so it doesn't require any separate
	! loads.  You can do this with images and with text strings.  I haven't found a
	! way to load sounds or media clips; they have to stay in external files.

	image BACKGROUND from "Family.jpg"

	! Create the window.
	create MyWindow
		title "This is some of the family"
		at 100 100
		size 570 373
	on close MyWindow exit
	show MyWindow

!	Now add a label to act as a background,
!	then render the image onto it.
	create Background in MyWindow
	set the icon of Background to BACKGROUND
	show Background

	stop
