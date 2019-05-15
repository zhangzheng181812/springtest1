import com.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by admin on 2019/3/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT ,classes = Application.class)
public class TestJdk8 {

    public interface MyPredicate<T> {
        boolean test(T t);
    }

    public class Product {
        int Color;

        @Override
        public String toString() {
            return "Product{" +
                    "Color=" + Color +
                    '}';
        }

        public Product(int color) {
            Color = color;
        }

        public int getColor() {
            return Color;
        }

        public void setColor(int color) {
            Color = color;
        }
    }

    public List<Product> filterProductByPredicate(Product[] list, MyPredicate<Product> mp) {
        List<Product> prods = new ArrayList<>();
        for (Product prod : list) {
            if (mp.test(prod)) {
                prods.add(prod);
            }

        }
        return prods;
    }

    Product p1 = new Product(1);
    Product p2 = new Product(2);
    Product[] list = new Product[]{p1, p2};


    @Test
    public void test1(){
        List<Product> products = filterProductByPredicate(list, new MyPredicate<Product>() {
            @Override
            public boolean test(Product product) {
                return product.getColor() > 1;
            }
        });
        System.out.println(products);
    }

    @Test
    public void test2(){
        List<Product> products = filterProductByPredicate(list,(p)->p.getColor()>1);
        System.out.println(products);
    }

    @Test
    public void test11 () {
        String z = strOperar("1", (x) -> x + x);
        System.out.println(z);
    }

    public void consumo (String money, Consumer<String> c) {
         c.accept(money);
    }

    public String  strOperar (String money, Function<String,String> c) {
        return c.apply(money);
    }

    @Test
    public void test12 () {
        Consumer<Integer> con = (x) -> System.out.println(x + x);
        con.accept(1);
    }
}