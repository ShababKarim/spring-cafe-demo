DELETE FROM menu_item;

INSERT INTO menu_item(id, name, ingredients, quantity, price) VALUES
    (0, 'Cappuccino', ARRAY ['milk', 'foam', 'espresso', 'sugar'], 100, 4.50),
    (1, 'Latte', ARRAY ['milk', 'foam', 'espresso', 'sugar', 'syrup'], 75, 5.50),
    (2, 'Iced Latte', ARRAY ['milk', 'foam', 'espresso', 'sugar', 'syrup', 'ice'], 50, 6.00);

DELETE FROM "order";