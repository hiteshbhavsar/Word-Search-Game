import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {


    private class Coordinate{
        int x;
        int y;
        public Coordinate(int x,int y)
        {
            this.x=x;
            this.y=y;
        }
    }

    private enum Direction{
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_INVERSE,
        VERTICAL_INVERSE,
        DIAGONAL_INVERSE
    }
    private int gridSize;
    private char[][] contents;
    private List<Coordinate> coordinates=new ArrayList<>();

    public Grid(int gridSize)
    {
        this.gridSize=gridSize;
        contents=new char[this.gridSize][this.gridSize];
        for(int i=0;i<gridSize;i++)
        {
            for(int j=0;j<gridSize;j++)
            {
                coordinates.add(new Coordinate(i,j));
                contents[i][j]='_';
            }
        }
    }

    public void fillGrid(List<String> words)
    {
        for(String word: words)
        {
            Collections.shuffle(coordinates);
            if(word.length()> gridSize)continue;
            //int x= ThreadLocalRandom.current().nextInt(0,gridSize);
            //int y= ThreadLocalRandom.current().nextInt(0,gridSize);
            for(Coordinate coordinate:coordinates)
            {
                int x=coordinate.x;
                int y=coordinate.y;
                Direction selectedDirection=getDirectiontoFit(word,coordinate);
                if(selectedDirection!=null)
                {
                    switch (selectedDirection) {
                        case HORIZONTAL:
                            for (char c : word.toCharArray()) {
                                contents[x][y++] = c;
                            }
                            break;
                        case VERTICAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y] = c;
                            }
                            break;
                        case DIAGONAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y++] = c;
                            }
                            break;
                        case HORIZONTAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x][y--] = c;
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y] = c;
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y--] = c;
                            }
                            break;
                    }
                }
                break;
            }
            //contents[x][y]=word.charAt(0);
        }
        fillGridwithRandomLetters();
    }

    public void fillGridwithRandomLetters()
    {
        String allCharacters="ABCDEFGHIJKLPMNOPRSTUVWXYZ";
        for(int i=0;i<gridSize;i++)
        {
            for(int j=0;j<gridSize;j++)
            {
                if(contents[i][j]=='_')
                {
                    int randomIndex=ThreadLocalRandom.current().nextInt(0,allCharacters.length());
                    contents[i][j]=allCharacters.charAt(randomIndex);
                }
            }
        }
    }

    public void displayGrid(){
        for(int i=0;i<gridSize;i++)
        {
            for(int j=0;j<gridSize;j++)
            {
                System.out.print(contents[i][j]+" ");
            }
            System.out.println();
        }
    }

    private boolean doesFit(String word,Coordinate coordinate)
    {
        if(coordinate.y+word.length()<gridSize)
        {
            for(int i=0;i<word.length();i++){
                if(contents[coordinate.x][coordinate.y+i]!='_') return false;
            }
            return true;
        }
        return false;
    }

    private Direction getDirectiontoFit(String word,Coordinate coordinate)
    {
        List<Direction> directions= Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for(Direction direction:directions)
        {
            if(doesFit(word,coordinate,direction))
            {
                return direction;
            }
        }
        return null;
    }

    private boolean doesFit(String word,Coordinate coordinate,Direction direction)
    {
        int wordLength=word.length();
        switch (direction) {
            case HORIZONTAL:
                if (coordinate.y + wordLength > gridSize)  return false;
                for(int i=0;i<word.length();i++)
                {
                    if(contents[coordinate.x][coordinate.y+i]!='_') return false;
                }
                break;
            case VERTICAL:
                if(coordinate.x+wordLength>gridSize)  return false;
                for(int i=0;i<word.length();i++)
                {
                    if(contents[coordinate.x+i][coordinate.y]!='_') return false;
                }
                break;
            case DIAGONAL:
                if(coordinate.x+wordLength>gridSize||coordinate.y+wordLength>gridSize) return false;
                for(int i=0;i<word.length();i++)
                {
                    if(contents[coordinate.x+i][coordinate.y+i]!='_') return false;
                }
                break;
            case HORIZONTAL_INVERSE:
                if(coordinate.y-wordLength<0) return false;
                for(int i=0;i<word.length();i++)
                {
                    if(contents[coordinate.x][coordinate.y-i]!='_') return false;
                }
                break;
            case VERTICAL_INVERSE:
                if(coordinate.x-wordLength<0) return false;
                for(int i=0;i<word.length();i++)
                {
                    if(contents[coordinate.x-i][coordinate.y]!='_') return false;
                }
                break;
            case DIAGONAL_INVERSE:
                if(coordinate.x-wordLength<0 || coordinate.y-wordLength<0) return false;
                for(int i=0;i<word.length();i++)
                {
                    if(contents[coordinate.x-i][coordinate.y-i]!='_') return false;
                }
                break;
        }
        return true;
    }
}
