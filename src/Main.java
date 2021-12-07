public class Main {
    public static void main(String[] args) {
        Road road = new Road(0, 3, Road.DIRECTION.EAST);
        Road road2 = new Road(1, 3, Road.DIRECTION.EAST);
        road.Connect(road2);
        Segment[] randomSegment = road.GetRandomSegments(3);

        Bus car = new Bus(0, randomSegment, 1);

        car.PrintInformation();
        for(int i = 0; i < 10; ++i)
        {
            car.Move();
            car.PrintInformation();
        }
    }
}