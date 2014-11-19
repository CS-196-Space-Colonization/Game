Input:
Right Mouse Click : Used to select objects (ie ships and such)
--------Hold shift to select multiple objects
--------Don't hold shift and select any point in space (ie not an object) to deselect all objects

Left Mouse hold: Used to change where the camera looks at

W: Camera move up		  /
A: Camera move left		 /
S: Camera move down		/========================== Camera movement will change to dragging the cursor
D: Camera move right		\========================== to the edge of the window. (Not implemented yet because you have
Q: Camera move up		 \			    to be in fullscreen mode for it to work).
Z: Camera move down		  \

Hotkeys for object actions (ie move, attack)
M: Pull up move cursor (then use left click to pick point)
R: Pull up rotate cursor (^^^)
F: Pull up attack cursor (^^^)
B: Pull up box select cursor
=================================================================================================================================
If you extend DrawableObject3d (and you're not abstract), you must do the following things:
--Have a static int variable used to keep count of the objects of that type made (I personally call mine M_ID_COUNT).
--Make a call to DrawableObject3d.setUserDataInfo(params...) and pass in a String that has the represents the name of the
  class calling this function (ie "Ship", "MoveableObject", "Star", etc.), as well as the current id count. <--- After you
  set the model!!!
