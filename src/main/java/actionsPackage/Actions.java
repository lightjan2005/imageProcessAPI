package actionsPackage;

public class Actions {

    private int flip; // 1 for horizontal and -1 for vertical
    private int rotateDegrees;
    private int greyscale; // 1 for convert to greyscale
    private double resizePercent;
    private int generateThumbnail; // 1 for generate
    private int rotate90; // 0 for rotateLeft and 1 for rotateRight

    public Actions(){}

    public Actions(int flip, int rotateDegrees, int greyscale, double resizePercent, int generateThumbnail, int rotate90){
        super();
        this.flip = flip;
        this.rotateDegrees = rotateDegrees;
        this.greyscale = greyscale;
        this.resizePercent = resizePercent;
        this.generateThumbnail = generateThumbnail;
        this.rotate90 = rotate90;

    }


    public int getFlip() {
        return flip;
    }

    public void setFlip(int flip) {
        this.flip = flip;
    }

    public int getRotateDegrees() {
        return rotateDegrees;
    }

    public void setRotateDegrees(int rotateDegrees) {
        this.rotateDegrees = rotateDegrees;
    }

    public int getGreyscale() {
        return greyscale;
    }

    public void setGreyscale(int greyscale) {
        this.greyscale = greyscale;
    }

    public double getResizePercent() {
        return resizePercent;
    }

    public void setResizePercent(double resizePercent) {
        this.resizePercent = resizePercent;
    }

    public int getGenerateThumbnail() {
        return generateThumbnail;
    }

    public void setGenerateThumbnail(int generateThumbnail) {
        this.generateThumbnail = generateThumbnail;
    }

    public int getRotate90() {
        return rotate90;
    }

    public void setRotate90(int rotate90) {
        this.rotate90 = rotate90;
    }
}
