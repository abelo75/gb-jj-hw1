import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Корзина
 * @param <T> Еда
 */
public class Cart <T extends Food>{

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    public Cart(Class<T> clazz, UMarket market)
    {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs(){
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                index.getAndIncrement(), food.getName(),
                food.getProteins() ? "Да" : "Нет",
                food.getFats() ? "Да" : "Нет",
                food.getCarbohydrates() ? "Да" : "Нет"));
    }

    /**
     * Балансировка корзины
     */
    public void cardBalancing()
    {
        boolean proteins = foodstuffs.stream().anyMatch(Food::getProteins);
        boolean fats = foodstuffs.stream().anyMatch(Food::getFats);
        boolean carbohydrates = foodstuffs.stream().anyMatch(Food::getCarbohydrates);

        if (proteins && fats && carbohydrates)
        {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        List<Food> allFood = market.getThings(Food.class).stream().toList();
        if (!proteins)
        {
            Food withProteins = allFood.stream().filter(Food::getProteins).findFirst().orElse(null);
            if (withProteins != null)
            {
                foodstuffs.add((T)withProteins);
                proteins = true;
            }
        }

        if (!fats)
        {
            Food withFats = allFood.stream().filter(Food::getFats).findFirst().orElse(null);
            if (withFats != null)
            {
                foodstuffs.add((T)withFats);
                fats = true;
            }
        }

        if (!carbohydrates)
        {
            Food withCarbohydrates = allFood.stream().filter(Food::getCarbohydrates).findFirst().orElse(null);
            if (withCarbohydrates != null)
            {
                foodstuffs.add((T)withCarbohydrates);
                carbohydrates = true;
            }
        }

        if (proteins && fats && carbohydrates)
            System.out.println("Корзина сбалансирована по БЖУ.");
        else
            System.out.println("Невозможно сбалансировать корзину по БЖУ.");

    }

}
