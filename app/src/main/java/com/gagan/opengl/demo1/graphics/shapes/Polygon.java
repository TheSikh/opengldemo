package com.gagan.opengl.demo1.graphics.shapes;

import android.util.Log;

import com.gagan.opengl.demo1.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by gagan on 5/1/17.
 * Single class can be used to draw Triangle, Square, Pentagon, Hexagon, Septagon, Octagon, circle, etc
 */

public class Polygon {

    private float center_x, center_y, radius;
    private int sides;
    private float[] vertices = null;
    private float[] textureCoords = null;
//    private short[] drawIndexOrder = null;

    public Polygon(float center_x, float center_y, // (x,y,z) center
                          float radius, // radius
                          int sides) // number of sides
    {
        this.center_x = center_x;
        this.center_y = center_y;
        this.radius = radius;
        this.sides = sides;
        calcVertices();
        calcTextureCoords();
//        calcDrawIndexOrder();
    }

    private void calcVertices() {
        // Outer vertices of the circle i.e. excluding the center_x, center_y
        int circumferencePoints = sides - 1;

        // Circle vertices and buffer variables
        int vertice = 0;
        vertices = new float[sides*2];

        // The initial buffer values
        vertices[vertice++] = center_x;
        vertices[vertice++] = center_y;

        // Set circle vertices values
        for (int i = 0; i < circumferencePoints; i++) {
            float percent = (i / (float) (circumferencePoints - 1));
            float radians = (float) (percent * 2 * Math.PI);


            // Vertex position
            float outer_x = (float) (center_x + radius * Math.cos(radians));
            float outer_y = (float) (center_y + radius * Math.sin(radians));

            vertices[vertice++] = outer_x;
            vertices[vertice++] = outer_y;
        }
        this.printArray(vertices, "vertices array");

    }



    private void calcTextureCoords() {
        // Outer vertices of the circle i.e. excluding the center_x, center_y
        int circumferencePoints = sides - 1;

        // Circle vertices and buffer variables
        int vertice = 0;
        textureCoords = new float[sides*2];

        float tCenter_x = center_x/1000, tCenter_y =center_y/1000, tRadius= radius/100;
        // The initial buffer values
        textureCoords[vertice++] = tCenter_x;
        textureCoords[vertice++] = tCenter_y;

        // Set circle vertices values
        for (int i = 0; i < circumferencePoints; i++) {
            float percent = (i / (float) (circumferencePoints - 1));
            float radians = (float) (percent * 2 * Math.PI);


            // Vertex position
            float tOuter_x = (float) (tCenter_x + tRadius * Math.cos(radians));
            float tOuter_y = (float) (tCenter_y + tRadius * Math.sin(radians));

            textureCoords[vertice++] = tOuter_x;
            textureCoords[vertice++] = tOuter_y;
        }
        this.printArray(textureCoords, "textureCoords array");

    }



//    private void calcDrawIndexOrder() {
//        drawIndexOrder = new short[sides*3];
//
//
//        for (int i=0;i<sides;i++)
//        {
//            short index1 = 0;
//            short index2 = (short)(i+1);
//            short index3 = (short)(i+2);
//            if (index3 == sides+1)
//            {
//                index3 = 1;
//            }
//            drawIndexOrder[i*3 + 0]=index1;
//            drawIndexOrder[i*3 + 1]=index2;
//            drawIndexOrder[i*3 + 2]=index3;
//        }
//        this.printShortArray(drawIndexOrder, "index array");
//
//    }



    public FloatBuffer getVertexBuffer() {
        return ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(vertices);
    }


    public FloatBuffer getTextureBuffer() {
        return ByteBuffer.allocateDirect(textureCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(textureCoords);
    }

//    public ShortBuffer getIndexBuffer()
//    {
//        return ByteBuffer.allocateDirect(drawIndexOrder.length * 2)
//                .order(ByteOrder.nativeOrder())
//                .asShortBuffer()
//                .put(drawIndexOrder);
//    }

    public int getSides() {
        return sides;
    }


    private void printArray(float array[], String tag) {
        StringBuilder sb = new StringBuilder(tag);
        for (int i = 0; i < array.length; i++) {
            sb.append(";").append(array[i]);
        }
        Log.d("hh", sb.toString());
    }

//    private void printShortArray(short array[], String tag) {
//        StringBuilder sb = new StringBuilder(tag);
//        for (int i = 0; i < array.length; i++) {
//            sb.append(";").append(array[i]);
//        }
//        Log.d(tag, sb.toString());
//    }
}
