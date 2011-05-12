!	Demo1.ls

!	Opens a window, colors the background and waits for the close box

	package basic
	package graphics

	window MyWindow

	create MyWindow
		title "This is a Linguist program"
		at 100 100
		size 400 200
	set the background color of MyWindow to 240 255 255
	on close MyWindow exit
	show MyWindow

	stop
