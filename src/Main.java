public class Main {
    public static void main(String[] args) {
        Road[] roads = new Road[4];

        IntersectionFourWay fourWay = new IntersectionFourWay();
        System.out.println("Intersection");
        fourWay.PrintIntersection();
        System.out.println("");

        for(int i = 0; i < 4; ++i)
        {
            Road newRoad = new Road(3, Road.DIRECTION.values()[i]);
            System.out.println("Road " + i + ", " + Road.DIRECTION.values()[i]);
            newRoad.PrintRoad();
            System.out.println("");
            fourWay.ConnectRoad(newRoad, Road.DIRECTION.OppositeDirection(Road.DIRECTION.values()[i]));

            roads[i] = newRoad;
        }

        Segment testSegment = roads[0].GetRandomSegment();
        Car car = new Car(0, testSegment, 1);

        System.out.println("Car Movement");
        for(int i = 0; i < 20; ++i)
        {
            car.Move();
        }
    }
}