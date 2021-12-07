public class Main {
    public static void main(String[] args) {
        Road[] roads = new Road[4];

        IntersectionFourWay fourWay = new IntersectionFourWay();
        System.out.println("Intersection");
        fourWay.PrintIntersection();

        for(int i = 0; i < 4; ++i)
        {
            Road newRoad = new Road(3, Road.DIRECTION.values()[i]);
            System.out.println("Road " + i + ", " + Road.DIRECTION.values()[i]);
            newRoad.PrintRoad();
            fourWay.ConnectRoad(newRoad, Road.DIRECTION.values()[i]);

            roads[i] = newRoad;
        }
//        Road testRoad = new Road(10, Road.DIRECTION.E);
//        testRoad.PrintRoad();

        Segment testSegment = roads[2].GetRandomSegment();
        Bus bus = new Bus(0, roads[2].GetRandomSegments(3), 1);
        Car car = new Car(0, testSegment, 1);

        System.out.println("Car Movement");
        for(int i = 0; i < 20; ++i)
        {
            bus.Move();
        }
    }
}