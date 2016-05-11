#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_Compression=4
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
Local $hWnd = WinWaitActive("Windows Security", "", 10)
ControlFocus($hWnd, "", "Edit1")
ControlSetText($hWnd, "", "Edit1", "")
Send($CmdLine[1])

ControlFocus($hWnd, "", "Edit2")
ControlSetText($hWnd, "", "Edit2", "")
Send($CmdLine[2])

; Wait for 1 seconds.
Sleep(1000)

Send("{ENTER}")
; Close the Notepad window using the handle returned by WinWait.
WinClose($hWnd)
