import com.orderbook.core.SimpleOrderBook;
import org.junit.Assert;
import org.junit.Test;

public class SimpleOrderBookTest {
    @Test
    public void test1() {

        SimpleOrderBook engine = new SimpleOrderBook();
        engine.addBuyOrder(9.9, 1000);
        engine.addBuyOrder(9.8, 3000);
        engine.addBuyOrder(9.7, 5000);

        engine.addSellOrder(10.1, 3000);
        engine.addSellOrder(10.2, 3000);
        engine.addSellOrder(10.3, 10000);

        Assert.assertEquals(3, engine.getSellSize());
        Assert.assertEquals(3, engine.getBuySize());

        Assert.assertEquals(9000, engine.getBuyOrderQty());
        Assert.assertEquals(16000, engine.getSellOrderQuantity());

        engine.printOrderBook();

        engine.addNewOrder(10.5, 17000, true);

        Assert.assertEquals(0, engine.getSellSize());
        Assert.assertEquals(4, engine.getBuySize());

        Assert.assertEquals(10000, engine.getBuyOrderQty());
        Assert.assertEquals(0, engine.getSellOrderQuantity());

        engine.addNewOrder(9.8, 60000, false);

        Assert.assertEquals(1, engine.getSellSize());
        Assert.assertEquals(1, engine.getBuySize());

        Assert.assertEquals(5000, engine.getBuyOrderQty());
        Assert.assertEquals(55000, engine.getSellOrderQuantity());

        engine.addNewOrder(9.8, 55000, true);

        Assert.assertEquals(0, engine.getSellSize());
        Assert.assertEquals(1, engine.getBuySize());

        Assert.assertEquals(5000, engine.getBuyOrderQty());
        Assert.assertEquals(0, engine.getSellOrderQuantity());

        engine.addNewOrder(1, 5000, false);

        Assert.assertEquals(0, engine.getSellSize());
        Assert.assertEquals(0, engine.getBuySize());

        Assert.assertEquals(0, engine.getBuyOrderQty());
        Assert.assertEquals(0, engine.getSellOrderQuantity());

    }
}
