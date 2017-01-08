package fr.alexislavaud.strikemania;

import fr.alexislavaud.strikemania.engine.RenderManager;
import fr.alexislavaud.strikemania.engine.Texture;
import fr.alexislavaud.strikemania.engine.Vector2f;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

/**
 * Created by Alexis Lavaud on 20/12/2016.
 */
public final class Level {
    private FloatBuffer levelVertices;
    private int numQuads;
    private boolean levelBuilt;
    private Texture tileset;

    public Level(Texture tileset) {
        this.levelBuilt = false;
        this.tileset = tileset;
    }


    public static void drawTexturedQuad(float x, float y, float w, float h, int xo, int yo, float size) {
        final float RENDER_FACTOR = 2.0f;

        GL11.glTexCoord2f((0 + xo) / size, (0 + yo) / size); GL11.glVertex2f(x * RENDER_FACTOR, y * RENDER_FACTOR);
        GL11.glTexCoord2f((1 + xo) / size, (0 + yo) / size); GL11.glVertex2f(x * RENDER_FACTOR + w * RENDER_FACTOR, y * RENDER_FACTOR);
        GL11.glTexCoord2f((1 + xo) / size, (1 + yo) / size); GL11.glVertex2f(x * RENDER_FACTOR + w * RENDER_FACTOR, y * RENDER_FACTOR + h * RENDER_FACTOR);
        GL11.glTexCoord2f((0 + xo) / size, (1 + yo) / size); GL11.glVertex2f(x * RENDER_FACTOR, y * RENDER_FACTOR + h * RENDER_FACTOR);
    }

    Object[][] walls;

    public void buildLevel() {
        walls = new Object[16][16];

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (i == 0 || i == 15 || j == 0 || j == 15) {
                    walls[i][j] = new Object();
                }

                if (i > 5 && i < 10 && j > 5 && j < 10) {
                    if (i % 2 == 0 && j % 2 == 0) {
                        walls[i][j] = new Object();
                    }
                }
            }
        }

/*        List<Vertex> vertices = new ArrayList<>();

        final int levelSizeX = 16, levelSizeY = 16;

        this.numQuads = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (walls[i][j]) {
                    final float tileSize = 0.2f;

                    int[][] tilePos = new int[][]
                            {
                                    { 0 , 0, },
                                    { 0 , 0, },
                                    { 0 , 0, },
                                    { 0 , 0, },
                            };

                    boolean leftNeighbor = (i > 0) && walls[i - 1][j];
                    boolean rightNeighbor = (i < levelSizeX - 1) && walls[i + 1][j];
                    boolean upNeighbor = (j > 0) && walls[i][j - 1];
                    boolean downNeighbor = (j < levelSizeY - 1) && walls[i][j + 1];
                  //  boolean upLeftNeighbor = (i > 0 && j > 0) && walls[i - 1][j - 1];
                   // boolean upRightNeighbor = (i < levelSizeX - 1 && j > 0) && walls[i + 1][j - 1];
                   // boolean downRightNeighbor = (i < levelSizeX - 1 && j < levelSizeY - 1) && walls[i + 1][j + 1];
                   // boolean downLeftNeighbor = (i > 0 && j < levelSizeY - 1) && walls[i - 1][j + 1];

                    if (leftNeighbor) {
                        tilePos[0][0] = 0;
                    }
                    else {
                        tilePos[0][0] = 1;
                    }

                    if (upNeighbor) {
                        tilePos[0][1] = 0;
                    }

                    vertices.add(new Vertex(new Vector2f(i - 0.5f, j - 0.5f),
                            new Vector2f(tileX * tileSize, tileY * tileSize)));
                    vertices.add(new Vertex(new Vector2f(i + 0.5f, j - 0.5f),
                            new Vector2f(tileX * tileSize + tileSize, tileY * tileSize)));
                    vertices.add(new Vertex(new Vector2f(i + 0.5f, j + 0.5f),
                            new Vector2f(tileX * tileSize + tileSize, tileY * tileSize + tileSize)));
                    vertices.add(new Vertex(new Vector2f(i - 0.5f, j + 0.5f),
                            new Vector2f(tileX * tileSize, tileY * tileSize + tileSize)));

                    numQuads++;
                }
            }
        }

        this.levelVertices = BufferUtils.createFloatBuffer(vertices.size() * 4);
        vertices.stream().forEach((vertex) ->
                levelVertices.put(vertex.position.x).put(vertex.position.y).put(vertex.uv.x).put(vertex.uv.y));
        levelVertices.flip();*/
    }

    public void render(RenderManager renderManager) {
        if (!levelBuilt) {
            buildLevel();

            levelBuilt = true;
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(32.0f, 32.0f, 0.0f);
  //      GL11.glScalef(32.0f, 32.0f, 0.0f);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tileset.getTextureID());

 /*       GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        levelVertices.position(0);
        GL11.glVertexPointer(2, GL11.GL_FLOAT, 16, levelVertices);
        levelVertices.position(2);
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 16, levelVertices);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, numQuads * 4);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);*/

        final int width = 16, height = 16;

        GL11.glBegin(GL11.GL_QUADS);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                boolean vu = true, vd = true, vl = true, vr = true, vur = true, vdr = true, vul = true, vdl = true;

   //             if (x - 1 < 0 || y - 1 < 0 || x + 1 >= width || y + 1 >= height || walls[x][y] == null) continue;

                if (x < width - 1 && walls[x + 1][y] != null) vr = false;
                if (x > 0 && walls[x - 1][y] != null) vl = false;
                if (y < height - 1 && walls[x][y + 1] != null) vd = false;
                if (y > 0 && walls[x][y - 1] != null) vu = false;
                if (x < width - 1 && y < height - 1 && walls[x + 1][y + 1] != null) vdr = false;
                if (x < width - 1 && y > 0 && walls[x + 1][y - 1] != null) vur = false;
                if (x > 0 && y < height - 1 && walls[x - 1][y + 1] != null) vdl = false;
                if (x > 0 && y > 0 && walls[x - 1][y - 1] != null) vul = false;

                if (walls[x][y] != null) {
                    int[] tileSprite = new int[]{1, 1, 1, 1, 1, 1, 1, 1};

                    if (vl) {
                        tileSprite[0] = 0;
                        tileSprite[1] = 1;
                        tileSprite[6] = 0;
                        tileSprite[7] = 1;
                    }

                    if (vr) {
                        tileSprite[2] = 2;
                        tileSprite[3] = 1;
                        tileSprite[4] = 2;
                        tileSprite[5] = 1;
                    }

                    if (vu) {
                        tileSprite[0] = 1;
                        tileSprite[1] = 0;
                        tileSprite[2] = 1;
                        tileSprite[3] = 0;

                        if (vr) {
                            tileSprite[2] = 2;
                            tileSprite[3] = 0;
                        }

                        if (vl) {
                            tileSprite[0] = 0;
                            tileSprite[1] = 0;
                        }

                    }

                    if (vd) {

                        tileSprite[4] = 1;
                        tileSprite[5] = 2;
                        tileSprite[6] = 1;
                        tileSprite[7] = 2;

                        if (vr) {
                            tileSprite[4] = 2;
                            tileSprite[5] = 2;
                        }

                        if (vl) {
                            tileSprite[6] = 0;
                            tileSprite[7] = 2;
                        }

                    }

                    if (vd && vr) {
                        tileSprite[4] = 2;
                        tileSprite[5] = 2;
                    }

                    if (vd && vl) {
                        tileSprite[6] = 0;
                        tileSprite[7] = 2;
                    }

                    if (vur && !vu && !vr) {
                        tileSprite[2] = 1;
                        tileSprite[3] = 3;
                    }

                    if (vdr && !vd && !vr) {
                        tileSprite[4] = 1;
                        tileSprite[5] = 4;
                    }

                    if (vul && !vu && !vl) {
                        tileSprite[0] = 0;
                        tileSprite[1] = 3;
                    }

                    if (vdl && !vd && !vl) {
                        tileSprite[6] = 0;
                        tileSprite[7] = 4;
                    }

                    final float size = 16.f;
                    final float halfSize = 8.0f; // 16/2
                    final int xo = 0, yo = 0;

                    drawTexturedQuad(x * size, y * size, halfSize, halfSize, xo + tileSprite[0], yo + tileSprite[1], 5);
                    drawTexturedQuad(x * size + halfSize,	y * size, halfSize, halfSize, xo + tileSprite[2], yo + tileSprite[3], 5);
                    drawTexturedQuad(x * size + halfSize,	y * size + halfSize, 	halfSize, halfSize, xo + tileSprite[4], yo + tileSprite[5], 5);
                    drawTexturedQuad(x * size, y * size + halfSize, halfSize, halfSize,xo + tileSprite[6], yo + tileSprite[7], 5);
                }
            }

        }

        GL11.glEnd();


        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }

    private class Vertex {
        public Vector2f position;
        public Vector2f uv;

        public Vertex() {
            this.position = new Vector2f();
            this.uv = new Vector2f();
        }

        public Vertex(Vector2f position, Vector2f uv) {
            this.position = position;
            this.uv = uv;
        }
    }
}
