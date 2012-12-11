MapMaker
========

A 2D tiled game map maker.

The main class is MapMaker.java

Maps are represented in four layers:

* Base: The bottom layer, which uses tiles such as water, grass and road.
* Overlay: The top layer, which paints fences and such over the base.
* Collision: A collision-detection layer, used to define where the player(s) may walk.
* Warp: Used to define links between maps.

<hr>
<h4>How to use:</h4>

Go to File -> New to create a new map, or Open to open a pre-existing map.

Use "wasd" or the arrow keys to move the map.

On the right, select the layer you wish to work on (base, overlay, collision, and warp), represented as buttons.

Next, select the specific tile you wish to paint with.

Next, press and hold the left mouse button over the map to fill in the tiles with your selection.

<i>Warp Layer</i>:

On the right, select the warp layer.

Click on the "+" button to add a new warp. This is defined as a map name and a point on that map to warp to.

Select the newly created warp tile on the right. (Labeled as a number).

Left-click or drag on the map to paint the warp.

Select the warp tile "0" and click/drag on the map to erase unwanted warp tiles.

Now players that walk over a tile painted with a warp will warp to the map and point indicated by the warp data.


