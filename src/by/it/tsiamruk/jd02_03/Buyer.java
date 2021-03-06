package by.it.tsiamruk.jd02_03;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Buyer implements Runnable, IBuyer, IBacket {

    private int number;
    private String name;
    boolean iWait = false;
    private boolean pensioner;
    double priceOfGood = 0;
    double totalAmount = 0;
    String goodName;
    Map<String, Double> backet = new HashMap<>();
    Semaphore buyersChoosingGoods = new Semaphore(10);


    public boolean isPensioner() {
        return pensioner;
    }

    public void setPensioner(boolean pensioner) {
        this.pensioner = pensioner;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Buyer(int number) {
        if (number % 4 == 0)
            setPensioner(true);
        this.number = number;
        if (isPensioner())
            this.setName("Пенсионер №" + number);
        this.setName("Покупатель №" + number);
    }

    @Override
    public void run() {
        enterToMarket();
        takeBacket();
        try {
            buyersChoosingGoods.acquire();
            chooseGoods();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            buyersChoosingGoods.release();
        }
        goToQueue();
        goToOut();
    }

    @Override
    public void enterToMarket() {
        System.out.println(this + " зашел в магазин");
    }

    @Override
    public void chooseGoods() {
            for (int i = 1; i < Helper.rnd(1, 4); i++) {
                if (isPensioner())
                    Helper.sleep(Helper.rnd(150, 300));
                else
                    Helper.sleep(Helper.rnd(100, 200));
                goodName = Goods.random();
                priceOfGood = Goods.getPrice();
                System.out.format("%s выбрал товар: %s цена: %.2f%n", this, goodName, priceOfGood);
                totalAmount += priceOfGood;
                System.out.format("%.2f стоят покупки %s%n", totalAmount, this);
                putGoodsToBacket();
            }
    }

    @Override
    public void goToQueue() {
        synchronized (this) {
            QueueBuyers.add(this);
            System.out.println(this + " встал в очередь");
            iWait = true;
            while (iWait) try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void goToOut() {
        System.out.println(this + " Вышел из магазина");
    }

    @Override
    public String toString() {
        if (isPensioner())
            return "Пенсионер №" + number;
        else
            return "Покупатель №" + number;
        //return this.getName();
    }

    @Override
    public void takeBacket() {

        if (isPensioner())
            Helper.sleep(Helper.rnd(150, 300));
        else
            Helper.sleep(Helper.rnd(100, 200));
        System.out.println(this + " взял корзину");
    }

    @Override
    public void putGoodsToBacket() {
        backet.put(goodName, priceOfGood);
        if (isPensioner())
            Helper.sleep(Helper.rnd(150, 300));
        else
            Helper.sleep(Helper.rnd(100, 200));
        System.out.println(this + " положил товар в корзину");
    }

}
