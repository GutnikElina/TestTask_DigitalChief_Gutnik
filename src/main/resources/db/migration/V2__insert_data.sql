INSERT INTO product (name, description, active, start_date) VALUES
                                                                ('Ноутбук Lenovo IdeaPad', 'Мощный ноутбук для работы и развлечений', TRUE, '2023-01-10'),
                                                                ('Ноутбук Samsung Galaxy', 'Ноутбук с высокой производительностью', TRUE, '2023-02-15'),
                                                                ('Ноутбук ASUS ZenBook', 'Компактный ноутбук с длительным временем работы', FALSE, '2023-03-05'),
                                                                ('Телевизор LG OLED', 'Высококачественный OLED-телевизор с ярким дисплеем', TRUE, '2023-04-20'),
                                                                ('Телевизор Samsung QLED', 'Технология QLED для ярких цветов', FALSE, '2023-05-11'),
                                                                ('Телевизор Sony Bravia', 'Современный телевизор с 4K разрешением', TRUE, '2023-06-01'),
                                                                ('Микроволновая печь Panasonic', 'Микроволновая печь с множеством функций', TRUE, '2023-07-18'),
                                                                ('Микроволновая печь LG', 'Быстрая и удобная микроволновая печь', FALSE, '2023-08-22'),
                                                                ('Микроволновая печь Samsung', 'Микроволновая печь с грилем', TRUE, '2023-09-14'),
                                                                ('Холодильник Atlant', 'Большой холодильник с экономным энергопотреблением', TRUE, '2023-10-05'),
                                                                ('Холодильник LG', 'Холодильник с функцией No Frost', TRUE, '2023-01-15'),
                                                                ('Холодильник Samsung', 'Современный холодильник с водяным диспенсером', FALSE, '2023-02-01'),
                                                                ('Пылесос Xiaomi Mi Robot', 'Умный робот-пылесос для идеальной уборки', FALSE, '2023-03-12'),
                                                                ('Пылесос Samsung', 'Мощный пылесос с циклонной системой', TRUE, '2023-04-07'),
                                                                ('Пылесос Philips', 'Пылесос с фильтром HEPA', TRUE, '2023-05-20'),
                                                                ('Фен Philips', 'Мощный фен с несколькими режимами', FALSE, '2023-06-30'),
                                                                ('Фен Dyson', 'Фен с технологией защиты от перегрева', TRUE, '2023-07-05'),
                                                                ('Фен Xiaomi', 'Фен с умными функциями и сушки', TRUE, '2023-08-17'),
                                                                ('Смарт-часы Apple Watch', 'Новые смарт-часы с расширенными функциями здоровья', FALSE, '2023-09-22'),
                                                                ('Смарт-часы Samsung Galaxy', 'Смарт-часы с длительным временем работы', TRUE, '2023-10-11'),
                                                                ('Смарт-часы Huawei Watch', 'Смарт-часы с высоким качеством сборки', FALSE, '2023-01-15');

INSERT INTO sku (product_id, sku_code, price) VALUES
                                                  (1, 'SKU-LENOVO-003', 1350.00),
                                                  (1, 'SKU-LENOVO-004', 1400.00),
                                                  (1, 'SKU-LENOVO-005', 1450.00),

                                                  (2, 'SKU-SAMSUNG-003', 1250.00),
                                                  (2, 'SKU-SAMSUNG-004', 1300.00),
                                                  (2, 'SKU-SAMSUNG-005', 1350.00),

                                                  (3, 'SKU-ASUS-003', 1500.00),
                                                  (3, 'SKU-ASUS-004', 1550.00),
                                                  (3, 'SKU-ASUS-005', 1600.00),

                                                  (4, 'SKU-LG-003', 2700.00),
                                                  (4, 'SKU-LG-004', 2800.00),
                                                  (4, 'SKU-LG-005', 2900.00),

                                                  (5, 'SKU-SAMSUNG-QLED-003', 3000.00),
                                                  (5, 'SKU-SAMSUNG-QLED-004', 3050.00),
                                                  (5, 'SKU-SAMSUNG-QLED-005', 3100.00),

                                                  (6, 'SKU-SONY-BRAVIA-003', 3100.00),
                                                  (6, 'SKU-SONY-BRAVIA-004', 3150.00),
                                                  (6, 'SKU-SONY-BRAVIA-005', 3200.00),

                                                  (7, 'SKU-PANASONIC-003', 240.00),
                                                  (7, 'SKU-PANASONIC-004', 260.00),
                                                  (7, 'SKU-PANASONIC-005', 280.00),

                                                  (8, 'SKU-LG-003', 220.00),
                                                  (8, 'SKU-LG-004', 240.00),
                                                  (8, 'SKU-LG-005', 260.00),

                                                  (9, 'SKU-SAMSUNG-003', 250.00),
                                                  (9, 'SKU-SAMSUNG-004', 270.00),
                                                  (9, 'SKU-SAMSUNG-005', 290.00),

                                                  (10, 'SKU-ATLANT-003', 700.00),
                                                  (10, 'SKU-ATLANT-004', 750.00),
                                                  (10, 'SKU-ATLANT-005', 800.00),

                                                  (11, 'SKU-LG-003', 800.00),
                                                  (11, 'SKU-LG-004', 850.00),
                                                  (11, 'SKU-LG-005', 900.00),

                                                  (12, 'SKU-SAMSUNG-003', 900.00),
                                                  (12, 'SKU-SAMSUNG-004', 950.00),
                                                  (12, 'SKU-SAMSUNG-005', 1000.00),

                                                  (13, 'SKU-XIAOMI-004', 400.00),
                                                  (13, 'SKU-XIAOMI-005', 450.00),
                                                  (13, 'SKU-XIAOMI-006', 480.00),

                                                  (14, 'SKU-SAMSUNG-003', 450.00),
                                                  (14, 'SKU-SAMSUNG-004', 480.00),
                                                  (14, 'SKU-SAMSUNG-005', 500.00),

                                                  (15, 'SKU-PHILIPS-003', 70.00),
                                                  (15, 'SKU-PHILIPS-004', 75.00),
                                                  (15, 'SKU-PHILIPS-005', 80.00),

                                                  (16, 'SKU-ARISTON-003', 370.00),
                                                  (16, 'SKU-ARISTON-004', 400.00),
                                                  (16, 'SKU-ARISTON-005', 450.00),

                                                  (17, 'SKU-TEFAL-003', 60.00),
                                                  (17, 'SKU-TEFAL-004', 70.00),
                                                  (17, 'SKU-TEFAL-005', 80.00),

                                                  (18, 'SKU-BRAUN-003', 90.00),
                                                  (18, 'SKU-BRAUN-004', 100.00),
                                                  (18, 'SKU-BRAUN-005', 110.00),

                                                  (19, 'SKU-EPSON-003', 1400.00),
                                                  (19, 'SKU-EPSON-004', 1450.00),
                                                  (19, 'SKU-EPSON-005', 1500.00),

                                                  (20, 'SKU-APPLE-004', 1250.00),
                                                  (20, 'SKU-APPLE-005', 1300.00),
                                                  (20, 'SKU-APPLE-006', 1350.00);