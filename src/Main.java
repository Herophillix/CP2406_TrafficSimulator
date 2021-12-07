public class Main {
    public static void main(String[] args) {
        Road road = new Road(10, Road.DIRECTION.E);
        System.out.println("Road 1");
        road.PrintRoad();
        Segment testSegment = road.GetRandomSegment();
        Car car = new Car(0, testSegment, 1);

        //Road road2 = new Road(10, Road.DIRECTION.E);
        //System.out.println("Road 2");
        //road2.PrintRoad();

        //road.Connect(road2);

        IntersectionFourWay fourWay = new IntersectionFourWay();
        fourWay.ConnectRoad(road, Road.DIRECTION.W);
        fourWay.PrintIntersection();

        System.out.println("Car Movement");
        for(int i = 0; i < 20; ++i)
        {
            if(i == 12)
            {
                System.out.println();
            }
            car.Move();
        }
    }
}