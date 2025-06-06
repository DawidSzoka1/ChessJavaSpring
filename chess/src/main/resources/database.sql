use chess_db;

DELETE
FROM figure_moves;
DELETE
FROM figure;
DELETE from move;
DELETE from ranking_position;
DELETE from ranking;
DELETE
FROM game;
DELETE from game_type;
DELETE
FROM users_roles;
DELETE
FROM role;
DELETE
FROM user;


INSERT INTO role
values (1, 'ROLE_BASE'),
       (2, 'ROLE_ADMIN');
# password is haslo
INSERT INTO user (enable, id, ranking, password, username, is_guest, authorization, created)
VALUES (true, 1, 25, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'jan_kowalski', FALSE, 'Admin',
        TIMESTAMP '2024-03-01 15:30:00'),
       (true, 2, 30, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'anna_nowak', FALSE, 'Admin',
        TIMESTAMP '2024-03-01 15:30:00'),
       (true, 3, 45, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'michal_adamczyk', FALSE,
        'Użytkownik', TIMESTAMP '2024-08-11 15:30:00'),
       (true, 4, 22, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'kasia_kwiatkowska', FALSE,
        'Użytkownik', TIMESTAMP '2024-08-12 15:30:00'),
       (true, 5, 35, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'tomasz_wisniewski', FALSE,
        'Użytkownik', TIMESTAMP '2024-08-5 15:30:00'),
       (true, 6, 50, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'agnieszka_lewandowska', FALSE,
        'Użytkownik', TIMESTAMP '2024-08-11 15:30:00'),
       (true, 7, 28, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'bartek_zielinski', FALSE,
        'Użytkownik', TIMESTAMP '2024-08-11 15:30:00'),
       (true, 8, 40, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'joanna_dabrowska', FALSE,
        'Użytkownik', TIMESTAMP '2024-08-11 15:30:00'),
       (true, 9, 33, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'mateusz_kaczmarek', FALSE,
        'Użytkownik', TIMESTAMP '2024-08-11 15:30:00'),
       (true, 10, 27, '$2a$12$cJ9u3iJfldUasYTScx6qrO7VqPpS/7XomSG/9/wrtIPC.Zb6YBV3y', 'aleksandra_pawlak', FALSE,
        'Użytkownik', TIMESTAMP '2024-08-11 15:30:00');
INSERT INTO users_roles(role_id, user_id)
VALUES (2, 1),
       (1, 1),
       (1, 2),
       (2, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10);

INSERT INTO game_type (type, time_per_player, add_per_move) VALUES
                                                                ('Bullet', 1.0, 0.0),         -- 1 minuta, bez dodawanego czasu
                                                                ('Blitz', 5.0, 0.0),          -- 5 minut, bez dodatku
                                                                ('Rapid', 15.0, 10.0),        -- 15 minut + 10 sekund na ruch
                                                                ('Classical', 30.0, 30.0),    -- 30 minut + 30 sekund na ruch
                                                                ('Custom', 10.0, 5.0);

INSERT INTO ranking (id, name, game_type)
VALUES (1, 'Ranking Błyskawiczny', 2),
       (2, 'Ranking Klasyczny', 4),
       (3, 'Ranking Bullet', 1);

INSERT INTO ranking_position (user_id, ranking_id, position, points) VALUES
                                                                         (1, 1, 1, 1800),
                                                                         (2, 1, 2, 1750),
                                                                         (3, 1, 3, 1700),
                                                                         (4, 1, 4, 1650),
                                                                         (5, 1, 5, 1600);

-- Ranking Klasyczny
INSERT INTO ranking_position (user_id, ranking_id, position, points) VALUES
                                                                         (6, 2, 1, 2000),
                                                                         (7, 2, 2, 1950),
                                                                         (8, 2, 3, 1900),
                                                                         (9, 2, 4, 1850),
                                                                         (10, 2, 5, 1800);

-- Ranking Bullet
INSERT INTO ranking_position (user_id, ranking_id, position, points) VALUES
                                                                         (2, 3, 1, 1600),
                                                                         (3, 3, 2, 1580),
                                                                         (4, 3, 3, 1550),
                                                                         (5, 3, 4, 1500),
                                                                         (6, 3, 5, 1480);

INSERT INTO game (black, white, winner, start, end, check_status, game_id, next_move, result, game_type_id, move_count)
VALUES (1, 2, 2, TIMESTAMP '2024-03-01 15:30:00', TIMESTAMP '2024-03-01 16:15:00', 'N', 'GME12345AB', 'W',
        'WHITE_WINS', 1, 0),
       (3, 4, null, TIMESTAMP '2024-03-02 18:00:00', TIMESTAMP '2024-03-02 18:45:00', 'N', 'GME67890CD', 'W', 'DRAW', 3, 0),
       (5, 6, 5, TIMESTAMP '2024-03-03 20:10:00', TIMESTAMP '2024-03-03 21:00:00', 'N', 'GME54321EF', 'W',
        'BLACK_WINS', 2, 0),
       (7, 8, null, TIMESTAMP '2024-03-05 11:15:00', TIMESTAMP '2024-03-05 12:05:00', 'N', 'GME98765GH', 'W', 'DRAW', 4, 0),
       (9, 10, null, TIMESTAMP '2024-03-06 14:40:00', TIMESTAMP '2024-03-06 15:20:00', 'N', 'GME19283IJ', 'W', 'DRAW', 5, 0),
       (2, 5, null, TIMESTAMP '2024-03-07 16:50:00', TIMESTAMP '2024-03-07 17:35:00', 'N', 'GME56789KL', 'W', 'DRAW', 1, 0),
       (4, 7, 4, TIMESTAMP '2024-03-08 19:20:00', TIMESTAMP '2024-03-08 20:10:00', 'N', 'GME34567MN', 'W',
        'BLACK_WINS',1, 0),
       (6, 9, 6, TIMESTAMP '2024-03-10 12:45:00', TIMESTAMP '2024-03-10 13:30:00', 'N', 'GME87654OP', 'W',
        'BLACK_WINS', 1, 0),
       (8, 1, null, TIMESTAMP '2024-03-11 21:30:00', TIMESTAMP '2024-03-11 22:15:00', 'N', 'GME23456QR', 'W', 'DRAW', 1, 0),
       (10, 3, 3, TIMESTAMP '2024-03-12 10:05:00', TIMESTAMP '2024-03-12 10:50:00', 'N', 'GME78901ST', 'W',
        'WHITE_WINS', 1, 0);



INSERT INTO figure (id, dtype, color, game_id, image_name, position, opponent, owner_id, direction)
VALUES (1, 'Rook', 'W', 'GME12345AB', 'W_rook.png', 'A1', 'B', 2, null),
       (2, 'Knight', 'W', 'GME12345AB', 'W_knight.png', 'B1', 'B', 2, null),
       (3, 'Bishop', 'W', 'GME12345AB', 'W_bishop.png', 'C1', 'B', 2, null),
       (4, 'Queen', 'W', 'GME12345AB', 'W_queen.png', 'D1', 'B', 2, null),
       (5, 'King', 'W', 'GME12345AB', 'W_king.png', 'E1', 'B', 2, null),
       (6, 'Bishop', 'W', 'GME12345AB', 'W_bishop.png', 'F1', 'B', 2, null),
       (7, 'Knight', 'W', 'GME12345AB', 'W_knight.png', 'G1', 'B', 2, null),
       (8, 'Rook', 'W', 'GME12345AB', 'W_rook.png', 'H1', 'B', 2, null),
       (9, 'Pawn', 'W', 'GME12345AB', 'W_pawn.png', 'A2', 'B', 2, 1),
       (10, 'Pawn', 'W', 'GME12345AB', 'W_pawn.png', 'B2', 'B', 2, 1),
       (11, 'Pawn', 'W', 'GME12345AB', 'W_pawn.png', 'C2', 'B', 2, 1),
       (12, 'Pawn', 'W', 'GME12345AB', 'W_pawn.png', 'D2', 'B', 2, 1),
       (13, 'Pawn', 'W', 'GME12345AB', 'W_pawn.png', 'E2', 'B', 2, 1),
       (14, 'Pawn', 'W', 'GME12345AB', 'W_pawn.png', 'F2', 'B', 2, 1),
       (15, 'Pawn', 'W', 'GME12345AB', 'W_pawn.png', 'G2', 'B', 2, 1),
       (16, 'Pawn', 'W', 'GME12345AB', 'W_pawn.png', 'H2', 'B', 2, 1),
       (17, 'Rook', 'B', 'GME12345AB', 'B_rook.png', 'A8', 'W', 1, null),
       (18, 'Knight', 'B', 'GME12345AB', 'B_knight.png', 'B8', 'W', 1, null),
       (19, 'Bishop', 'B', 'GME12345AB', 'B_bishop.png', 'C8', 'W', 1, null),
       (20, 'Queen', 'B', 'GME12345AB', 'B_queen.png', 'D8', 'W', 1, null),
       (21, 'King', 'B', 'GME12345AB', 'B_king.png', 'E8', 'W', 1, null),
       (22, 'Bishop', 'B', 'GME12345AB', 'B_bishop.png', 'F8', 'W', 1, null),
       (23, 'Knight', 'B', 'GME12345AB', 'B_knight.png', 'G8', 'W', 1, null),
       (24, 'Rook', 'B', 'GME12345AB', 'B_rook.png', 'H8', 'W', 1, null),
       (25, 'Pawn', 'B', 'GME12345AB', 'B_pawn.png', 'A7', 'W', 1, -1),
       (26, 'Pawn', 'B', 'GME12345AB', 'B_pawn.png', 'B7', 'W', 1, -1),
       (27, 'Pawn', 'B', 'GME12345AB', 'B_pawn.png', 'C7', 'W', 1, -1),
       (28, 'Pawn', 'B', 'GME12345AB', 'B_pawn.png', 'D7', 'W', 1, -1),
       (29, 'Pawn', 'B', 'GME12345AB', 'B_pawn.png', 'E7', 'W', 1, -1),
       (30, 'Pawn', 'B', 'GME12345AB', 'B_pawn.png', 'F7', 'W', 1, -1),
       (31, 'Pawn', 'B', 'GME12345AB', 'B_pawn.png', 'G7', 'W', 1, -1),
       (32, 'Pawn', 'B', 'GME12345AB', 'B_pawn.png', 'H7', 'W', 1, -1),
       (33, 'Rook', 'W', 'GME67890CD', 'W_rook.png', 'A1', 'B', 4, null),
       (34, 'Knight', 'W', 'GME67890CD', 'W_knight.png', 'B1', 'B', 4, null),
       (35, 'Bishop', 'W', 'GME67890CD', 'W_bishop.png', 'C1', 'B', 4, null),
       (36, 'Queen', 'W', 'GME67890CD', 'W_queen.png', 'D1', 'B', 4, null),
       (37, 'King', 'W', 'GME67890CD', 'W_king.png', 'E1', 'B', 4, null),
       (38, 'Bishop', 'W', 'GME67890CD', 'W_bishop.png', 'F1', 'B', 4, null),
       (39, 'Knight', 'W', 'GME67890CD', 'W_knight.png', 'G1', 'B', 4, null),
       (40, 'Rook', 'W', 'GME67890CD', 'W_rook.png', 'H1', 'B', 4, null),
       (41, 'Pawn', 'W', 'GME67890CD', 'W_pawn.png', 'A2', 'B', 4, 1),
       (42, 'Pawn', 'W', 'GME67890CD', 'W_pawn.png', 'B2', 'B', 4, 1),
       (43, 'Pawn', 'W', 'GME67890CD', 'W_pawn.png', 'C2', 'B', 4, 1),
       (44, 'Pawn', 'W', 'GME67890CD', 'W_pawn.png', 'D2', 'B', 4, 1),
       (45, 'Pawn', 'W', 'GME67890CD', 'W_pawn.png', 'E2', 'B', 4, 1),
       (46, 'Pawn', 'W', 'GME67890CD', 'W_pawn.png', 'F2', 'B', 4, 1),
       (47, 'Pawn', 'W', 'GME67890CD', 'W_pawn.png', 'G2', 'B', 4, 1),
       (48, 'Pawn', 'W', 'GME67890CD', 'W_pawn.png', 'H2', 'B', 4, 1),
       (49, 'Rook', 'B', 'GME67890CD', 'B_rook.png', 'A8', 'W', 3, null),
       (50, 'Knight', 'B', 'GME67890CD', 'B_knight.png', 'B8', 'W', 3, null),
       (51, 'Bishop', 'B', 'GME67890CD', 'B_bishop.png', 'C8', 'W', 3, null),
       (52, 'Queen', 'B', 'GME67890CD', 'B_queen.png', 'D8', 'W', 3, null),
       (53, 'King', 'B', 'GME67890CD', 'B_king.png', 'E8', 'W', 3, null),
       (54, 'Bishop', 'B', 'GME67890CD', 'B_bishop.png', 'F8', 'W', 3, null),
       (55, 'Knight', 'B', 'GME67890CD', 'B_knight.png', 'G8', 'W', 3, null),
       (56, 'Rook', 'B', 'GME67890CD', 'B_rook.png', 'H8', 'W', 3, null),
       (57, 'Pawn', 'B', 'GME67890CD', 'B_pawn.png', 'A7', 'W', 3, -1),
       (58, 'Pawn', 'B', 'GME67890CD', 'B_pawn.png', 'B7', 'W', 3, -1),
       (59, 'Pawn', 'B', 'GME67890CD', 'B_pawn.png', 'C7', 'W', 3, -1),
       (60, 'Pawn', 'B', 'GME67890CD', 'B_pawn.png', 'D7', 'W', 3, -1),
       (61, 'Pawn', 'B', 'GME67890CD', 'B_pawn.png', 'E7', 'W', 3, -1),
       (62, 'Pawn', 'B', 'GME67890CD', 'B_pawn.png', 'F7', 'W', 3, -1),
       (63, 'Pawn', 'B', 'GME67890CD', 'B_pawn.png', 'G7', 'W', 3, -1),
       (64, 'Pawn', 'B', 'GME67890CD', 'B_pawn.png', 'H7', 'W', 3, -1),
       (65, 'Rook', 'W', 'GME54321EF', 'W_rook.png', 'A1', 'B', 6, null),
       (66, 'Knight', 'W', 'GME54321EF', 'W_knight.png', 'B1', 'B', 6, null),
       (67, 'Bishop', 'W', 'GME54321EF', 'W_bishop.png', 'C1', 'B', 6, null),
       (68, 'Queen', 'W', 'GME54321EF', 'W_queen.png', 'D1', 'B', 6, null),
       (69, 'King', 'W', 'GME54321EF', 'W_king.png', 'E1', 'B', 6, null),
       (70, 'Bishop', 'W', 'GME54321EF', 'W_bishop.png', 'F1', 'B', 6, null),
       (71, 'Knight', 'W', 'GME54321EF', 'W_knight.png', 'G1', 'B', 6, null),
       (72, 'Rook', 'W', 'GME54321EF', 'W_rook.png', 'H1', 'B', 6, null),
       (73, 'Pawn', 'W', 'GME54321EF', 'W_pawn.png', 'A2', 'B', 6, 1),
       (74, 'Pawn', 'W', 'GME54321EF', 'W_pawn.png', 'B2', 'B', 6, 1),
       (75, 'Pawn', 'W', 'GME54321EF', 'W_pawn.png', 'C2', 'B', 6, 1),
       (76, 'Pawn', 'W', 'GME54321EF', 'W_pawn.png', 'D2', 'B', 6, 1),
       (77, 'Pawn', 'W', 'GME54321EF', 'W_pawn.png', 'E2', 'B', 6, 1),
       (78, 'Pawn', 'W', 'GME54321EF', 'W_pawn.png', 'F2', 'B', 6, 1),
       (79, 'Pawn', 'W', 'GME54321EF', 'W_pawn.png', 'G2', 'B', 6, 1),
       (80, 'Pawn', 'W', 'GME54321EF', 'W_pawn.png', 'H2', 'B', 6, 1),
       (81, 'Rook', 'B', 'GME54321EF', 'B_rook.png', 'A8', 'W', 5, null),
       (82, 'Knight', 'B', 'GME54321EF', 'B_knight.png', 'B8', 'W', 5, null),
       (83, 'Bishop', 'B', 'GME54321EF', 'B_bishop.png', 'C8', 'W', 5, null),
       (84, 'Queen', 'B', 'GME54321EF', 'B_queen.png', 'D8', 'W', 5, null),
       (85, 'King', 'B', 'GME54321EF', 'B_king.png', 'E8', 'W', 5, null),
       (86, 'Bishop', 'B', 'GME54321EF', 'B_bishop.png', 'F8', 'W', 5, null),
       (87, 'Knight', 'B', 'GME54321EF', 'B_knight.png', 'G8', 'W', 5, null),
       (88, 'Rook', 'B', 'GME54321EF', 'B_rook.png', 'H8', 'W', 5, null),
       (89, 'Pawn', 'B', 'GME54321EF', 'B_pawn.png', 'A7', 'W', 5, -1),
       (90, 'Pawn', 'B', 'GME54321EF', 'B_pawn.png', 'B7', 'W', 5, -1),
       (91, 'Pawn', 'B', 'GME54321EF', 'B_pawn.png', 'C7', 'W', 5, -1),
       (92, 'Pawn', 'B', 'GME54321EF', 'B_pawn.png', 'D7', 'W', 5, -1),
       (93, 'Pawn', 'B', 'GME54321EF', 'B_pawn.png', 'E7', 'W', 5, -1),
       (94, 'Pawn', 'B', 'GME54321EF', 'B_pawn.png', 'F7', 'W', 5, -1),
       (95, 'Pawn', 'B', 'GME54321EF', 'B_pawn.png', 'G7', 'W', 5, -1),
       (96, 'Pawn', 'B', 'GME54321EF', 'B_pawn.png', 'H7', 'W', 5, -1),
       (97, 'Rook', 'W', 'GME98765GH', 'W_rook.png', 'A1', 'B', 8, null),
       (98, 'Knight', 'W', 'GME98765GH', 'W_knight.png', 'B1', 'B', 8, null),
       (99, 'Bishop', 'W', 'GME98765GH', 'W_bishop.png', 'C1', 'B', 8, null),
       (100, 'Queen', 'W', 'GME98765GH', 'W_queen.png', 'D1', 'B', 8, null),
       (101, 'King', 'W', 'GME98765GH', 'W_king.png', 'E1', 'B', 8, null),
       (102, 'Bishop', 'W', 'GME98765GH', 'W_bishop.png', 'F1', 'B', 8, null),
       (103, 'Knight', 'W', 'GME98765GH', 'W_knight.png', 'G1', 'B', 8, null),
       (104, 'Rook', 'W', 'GME98765GH', 'W_rook.png', 'H1', 'B', 8, null),
       (105, 'Pawn', 'W', 'GME98765GH', 'W_pawn.png', 'A2', 'B', 8, 1),
       (106, 'Pawn', 'W', 'GME98765GH', 'W_pawn.png', 'B2', 'B', 8, 1),
       (107, 'Pawn', 'W', 'GME98765GH', 'W_pawn.png', 'C2', 'B', 8, 1),
       (108, 'Pawn', 'W', 'GME98765GH', 'W_pawn.png', 'D2', 'B', 8, 1),
       (109, 'Pawn', 'W', 'GME98765GH', 'W_pawn.png', 'E2', 'B', 8, 1),
       (110, 'Pawn', 'W', 'GME98765GH', 'W_pawn.png', 'F2', 'B', 8, 1),
       (111, 'Pawn', 'W', 'GME98765GH', 'W_pawn.png', 'G2', 'B', 8, 1),
       (112, 'Pawn', 'W', 'GME98765GH', 'W_pawn.png', 'H2', 'B', 8, 1),
       (113, 'Rook', 'B', 'GME98765GH', 'B_rook.png', 'A8', 'W', 7, null),
       (114, 'Knight', 'B', 'GME98765GH', 'B_knight.png', 'B8', 'W', 7, null),
       (115, 'Bishop', 'B', 'GME98765GH', 'B_bishop.png', 'C8', 'W', 7, null),
       (116, 'Queen', 'B', 'GME98765GH', 'B_queen.png', 'D8', 'W', 7, null),
       (117, 'King', 'B', 'GME98765GH', 'B_king.png', 'E8', 'W', 7, null),
       (118, 'Bishop', 'B', 'GME98765GH', 'B_bishop.png', 'F8', 'W', 7, null),
       (119, 'Knight', 'B', 'GME98765GH', 'B_knight.png', 'G8', 'W', 7, null),
       (120, 'Rook', 'B', 'GME98765GH', 'B_rook.png', 'H8', 'W', 7, null),
       (121, 'Pawn', 'B', 'GME98765GH', 'B_pawn.png', 'A7', 'W', 7, -1),
       (122, 'Pawn', 'B', 'GME98765GH', 'B_pawn.png', 'B7', 'W', 7, -1),
       (123, 'Pawn', 'B', 'GME98765GH', 'B_pawn.png', 'C7', 'W', 7, -1),
       (124, 'Pawn', 'B', 'GME98765GH', 'B_pawn.png', 'D7', 'W', 7, -1),
       (125, 'Pawn', 'B', 'GME98765GH', 'B_pawn.png', 'E7', 'W', 7, -1),
       (126, 'Pawn', 'B', 'GME98765GH', 'B_pawn.png', 'F7', 'W', 7, -1),
       (127, 'Pawn', 'B', 'GME98765GH', 'B_pawn.png', 'G7', 'W', 7, -1),
       (128, 'Pawn', 'B', 'GME98765GH', 'B_pawn.png', 'H7', 'W', 7, -1),
       (129, 'Rook', 'W', 'GME19283IJ', 'W_rook.png', 'A1', 'B', 10, null),
       (130, 'Knight', 'W', 'GME19283IJ', 'W_knight.png', 'B1', 'B', 10, null),
       (131, 'Bishop', 'W', 'GME19283IJ', 'W_bishop.png', 'C1', 'B', 10, null),
       (132, 'Queen', 'W', 'GME19283IJ', 'W_queen.png', 'D1', 'B', 10, null),
       (133, 'King', 'W', 'GME19283IJ', 'W_king.png', 'E1', 'B', 10, null),
       (134, 'Bishop', 'W', 'GME19283IJ', 'W_bishop.png', 'F1', 'B', 10, null),
       (135, 'Knight', 'W', 'GME19283IJ', 'W_knight.png', 'G1', 'B', 10, null),
       (136, 'Rook', 'W', 'GME19283IJ', 'W_rook.png', 'H1', 'B', 10, null),
       (137, 'Pawn', 'W', 'GME19283IJ', 'W_pawn.png', 'A2', 'B', 10, 1),
       (138, 'Pawn', 'W', 'GME19283IJ', 'W_pawn.png', 'B2', 'B', 10, 1),
       (139, 'Pawn', 'W', 'GME19283IJ', 'W_pawn.png', 'C2', 'B', 10, 1),
       (140, 'Pawn', 'W', 'GME19283IJ', 'W_pawn.png', 'D2', 'B', 10, 1),
       (141, 'Pawn', 'W', 'GME19283IJ', 'W_pawn.png', 'E2', 'B', 10, 1),
       (142, 'Pawn', 'W', 'GME19283IJ', 'W_pawn.png', 'F2', 'B', 10, 1),
       (143, 'Pawn', 'W', 'GME19283IJ', 'W_pawn.png', 'G2', 'B', 10, 1),
       (144, 'Pawn', 'W', 'GME19283IJ', 'W_pawn.png', 'H2', 'B', 10, 1),
       (145, 'Rook', 'B', 'GME19283IJ', 'B_rook.png', 'A8', 'W', 9, null),
       (146, 'Knight', 'B', 'GME19283IJ', 'B_knight.png', 'B8', 'W', 9, null),
       (147, 'Bishop', 'B', 'GME19283IJ', 'B_bishop.png', 'C8', 'W', 9, null),
       (148, 'Queen', 'B', 'GME19283IJ', 'B_queen.png', 'D8', 'W', 9, null),
       (149, 'King', 'B', 'GME19283IJ', 'B_king.png', 'E8', 'W', 9, null),
       (150, 'Bishop', 'B', 'GME19283IJ', 'B_bishop.png', 'F8', 'W', 9, null),
       (151, 'Knight', 'B', 'GME19283IJ', 'B_knight.png', 'G8', 'W', 9, null),
       (152, 'Rook', 'B', 'GME19283IJ', 'B_rook.png', 'H8', 'W', 9, null),
       (153, 'Pawn', 'B', 'GME19283IJ', 'B_pawn.png', 'A7', 'W', 9, -1),
       (154, 'Pawn', 'B', 'GME19283IJ', 'B_pawn.png', 'B7', 'W', 9, -1),
       (155, 'Pawn', 'B', 'GME19283IJ', 'B_pawn.png', 'C7', 'W', 9, -1),
       (156, 'Pawn', 'B', 'GME19283IJ', 'B_pawn.png', 'D7', 'W', 9, -1),
       (157, 'Pawn', 'B', 'GME19283IJ', 'B_pawn.png', 'E7', 'W', 9, -1),
       (158, 'Pawn', 'B', 'GME19283IJ', 'B_pawn.png', 'F7', 'W', 9, -1),
       (159, 'Pawn', 'B', 'GME19283IJ', 'B_pawn.png', 'G7', 'W', 9, -1),
       (160, 'Pawn', 'B', 'GME19283IJ', 'B_pawn.png', 'H7', 'W', 9, -1),
       (161, 'Rook', 'W', 'GME56789KL', 'W_rook.png', 'A1', 'B', 5, null),
       (162, 'Knight', 'W', 'GME56789KL', 'W_knight.png', 'B1', 'B', 5, null),
       (163, 'Bishop', 'W', 'GME56789KL', 'W_bishop.png', 'C1', 'B', 5, null),
       (164, 'Queen', 'W', 'GME56789KL', 'W_queen.png', 'D1', 'B', 5, null),
       (165, 'King', 'W', 'GME56789KL', 'W_king.png', 'E1', 'B', 5, null),
       (166, 'Bishop', 'W', 'GME56789KL', 'W_bishop.png', 'F1', 'B', 5, null),
       (167, 'Knight', 'W', 'GME56789KL', 'W_knight.png', 'G1', 'B', 5, null),
       (168, 'Rook', 'W', 'GME56789KL', 'W_rook.png', 'H1', 'B', 5, null),
       (169, 'Pawn', 'W', 'GME56789KL', 'W_pawn.png', 'A2', 'B', 5, 1),
       (170, 'Pawn', 'W', 'GME56789KL', 'W_pawn.png', 'B2', 'B', 5, 1),
       (171, 'Pawn', 'W', 'GME56789KL', 'W_pawn.png', 'C2', 'B', 5, 1),
       (172, 'Pawn', 'W', 'GME56789KL', 'W_pawn.png', 'D2', 'B', 5, 1),
       (173, 'Pawn', 'W', 'GME56789KL', 'W_pawn.png', 'E2', 'B', 5, 1),
       (174, 'Pawn', 'W', 'GME56789KL', 'W_pawn.png', 'F2', 'B', 5, 1),
       (175, 'Pawn', 'W', 'GME56789KL', 'W_pawn.png', 'G2', 'B', 5, 1),
       (176, 'Pawn', 'W', 'GME56789KL', 'W_pawn.png', 'H2', 'B', 5, 1),
       (177, 'Rook', 'B', 'GME56789KL', 'B_rook.png', 'A8', 'W', 2, null),
       (178, 'Knight', 'B', 'GME56789KL', 'B_knight.png', 'B8', 'W', 2, null),
       (179, 'Bishop', 'B', 'GME56789KL', 'B_bishop.png', 'C8', 'W', 2, null),
       (180, 'Queen', 'B', 'GME56789KL', 'B_queen.png', 'D8', 'W', 2, null),
       (181, 'King', 'B', 'GME56789KL', 'B_king.png', 'E8', 'W', 2, null),
       (182, 'Bishop', 'B', 'GME56789KL', 'B_bishop.png', 'F8', 'W', 2, null),
       (183, 'Knight', 'B', 'GME56789KL', 'B_knight.png', 'G8', 'W', 2, null),
       (184, 'Rook', 'B', 'GME56789KL', 'B_rook.png', 'H8', 'W', 2, null),
       (185, 'Pawn', 'B', 'GME56789KL', 'B_pawn.png', 'A7', 'W', 2, -1),
       (186, 'Pawn', 'B', 'GME56789KL', 'B_pawn.png', 'B7', 'W', 2, -1),
       (187, 'Pawn', 'B', 'GME56789KL', 'B_pawn.png', 'C7', 'W', 2, -1),
       (188, 'Pawn', 'B', 'GME56789KL', 'B_pawn.png', 'D7', 'W', 2, -1),
       (189, 'Pawn', 'B', 'GME56789KL', 'B_pawn.png', 'E7', 'W', 2, -1),
       (190, 'Pawn', 'B', 'GME56789KL', 'B_pawn.png', 'F7', 'W', 2, -1),
       (191, 'Pawn', 'B', 'GME56789KL', 'B_pawn.png', 'G7', 'W', 2, -1),
       (192, 'Pawn', 'B', 'GME56789KL', 'B_pawn.png', 'H7', 'W', 2, -1),
       (193, 'Rook', 'W', 'GME34567MN', 'W_rook.png', 'A1', 'B', 7, null),
       (194, 'Knight', 'W', 'GME34567MN', 'W_knight.png', 'B1', 'B', 7, null),
       (195, 'Bishop', 'W', 'GME34567MN', 'W_bishop.png', 'C1', 'B', 7, null),
       (196, 'Queen', 'W', 'GME34567MN', 'W_queen.png', 'D1', 'B', 7, null),
       (197, 'King', 'W', 'GME34567MN', 'W_king.png', 'E1', 'B', 7, null),
       (198, 'Bishop', 'W', 'GME34567MN', 'W_bishop.png', 'F1', 'B', 7, null),
       (199, 'Knight', 'W', 'GME34567MN', 'W_knight.png', 'G1', 'B', 7, null),
       (200, 'Rook', 'W', 'GME34567MN', 'W_rook.png', 'H1', 'B', 7, null),
       (201, 'Pawn', 'W', 'GME34567MN', 'W_pawn.png', 'A2', 'B', 7, 1),
       (202, 'Pawn', 'W', 'GME34567MN', 'W_pawn.png', 'B2', 'B', 7, 1),
       (203, 'Pawn', 'W', 'GME34567MN', 'W_pawn.png', 'C2', 'B', 7, 1),
       (204, 'Pawn', 'W', 'GME34567MN', 'W_pawn.png', 'D2', 'B', 7, 1),
       (205, 'Pawn', 'W', 'GME34567MN', 'W_pawn.png', 'E2', 'B', 7, 1),
       (206, 'Pawn', 'W', 'GME34567MN', 'W_pawn.png', 'F2', 'B', 7, 1),
       (207, 'Pawn', 'W', 'GME34567MN', 'W_pawn.png', 'G2', 'B', 7, 1),
       (208, 'Pawn', 'W', 'GME34567MN', 'W_pawn.png', 'H2', 'B', 7, 1),
       (209, 'Rook', 'B', 'GME34567MN', 'B_rook.png', 'A8', 'W', 4, null),
       (210, 'Knight', 'B', 'GME34567MN', 'B_knight.png', 'B8', 'W', 4, null),
       (211, 'Bishop', 'B', 'GME34567MN', 'B_bishop.png', 'C8', 'W', 4, null),
       (212, 'Queen', 'B', 'GME34567MN', 'B_queen.png', 'D8', 'W', 4, null),
       (213, 'King', 'B', 'GME34567MN', 'B_king.png', 'E8', 'W', 4, null),
       (214, 'Bishop', 'B', 'GME34567MN', 'B_bishop.png', 'F8', 'W', 4, null),
       (215, 'Knight', 'B', 'GME34567MN', 'B_knight.png', 'G8', 'W', 4, null),
       (216, 'Rook', 'B', 'GME34567MN', 'B_rook.png', 'H8', 'W', 4, null),
       (217, 'Pawn', 'B', 'GME34567MN', 'B_pawn.png', 'A7', 'W', 4, -1),
       (218, 'Pawn', 'B', 'GME34567MN', 'B_pawn.png', 'B7', 'W', 4, -1),
       (219, 'Pawn', 'B', 'GME34567MN', 'B_pawn.png', 'C7', 'W', 4, -1),
       (220, 'Pawn', 'B', 'GME34567MN', 'B_pawn.png', 'D7', 'W', 4, -1),
       (221, 'Pawn', 'B', 'GME34567MN', 'B_pawn.png', 'E7', 'W', 4, -1),
       (222, 'Pawn', 'B', 'GME34567MN', 'B_pawn.png', 'F7', 'W', 4, -1),
       (223, 'Pawn', 'B', 'GME34567MN', 'B_pawn.png', 'G7', 'W', 4, -1),
       (224, 'Pawn', 'B', 'GME34567MN', 'B_pawn.png', 'H7', 'W', 4, -1),
       (225, 'Rook', 'W', 'GME87654OP', 'W_rook.png', 'A1', 'B', 9, null),
       (226, 'Knight', 'W', 'GME87654OP', 'W_knight.png', 'B1', 'B', 9, null),
       (227, 'Bishop', 'W', 'GME87654OP', 'W_bishop.png', 'C1', 'B', 9, null),
       (228, 'Queen', 'W', 'GME87654OP', 'W_queen.png', 'D1', 'B', 9, null),
       (229, 'King', 'W', 'GME87654OP', 'W_king.png', 'E1', 'B', 9, null),
       (230, 'Bishop', 'W', 'GME87654OP', 'W_bishop.png', 'F1', 'B', 9, null),
       (231, 'Knight', 'W', 'GME87654OP', 'W_knight.png', 'G1', 'B', 9, null),
       (232, 'Rook', 'W', 'GME87654OP', 'W_rook.png', 'H1', 'B', 9, null),
       (233, 'Pawn', 'W', 'GME87654OP', 'W_pawn.png', 'A2', 'B', 9, 1),
       (234, 'Pawn', 'W', 'GME87654OP', 'W_pawn.png', 'B2', 'B', 9, 1),
       (235, 'Pawn', 'W', 'GME87654OP', 'W_pawn.png', 'C2', 'B', 9, 1),
       (236, 'Pawn', 'W', 'GME87654OP', 'W_pawn.png', 'D2', 'B', 9, 1),
       (237, 'Pawn', 'W', 'GME87654OP', 'W_pawn.png', 'E2', 'B', 9, 1),
       (238, 'Pawn', 'W', 'GME87654OP', 'W_pawn.png', 'F2', 'B', 9, 1),
       (239, 'Pawn', 'W', 'GME87654OP', 'W_pawn.png', 'G2', 'B', 9, 1),
       (240, 'Pawn', 'W', 'GME87654OP', 'W_pawn.png', 'H2', 'B', 9, 1),
       (241, 'Rook', 'B', 'GME87654OP', 'B_rook.png', 'A8', 'W', 6, null),
       (242, 'Knight', 'B', 'GME87654OP', 'B_knight.png', 'B8', 'W', 6, null),
       (243, 'Bishop', 'B', 'GME87654OP', 'B_bishop.png', 'C8', 'W', 6, null),
       (244, 'Queen', 'B', 'GME87654OP', 'B_queen.png', 'D8', 'W', 6, null),
       (245, 'King', 'B', 'GME87654OP', 'B_king.png', 'E8', 'W', 6, null),
       (246, 'Bishop', 'B', 'GME87654OP', 'B_bishop.png', 'F8', 'W', 6, null),
       (247, 'Knight', 'B', 'GME87654OP', 'B_knight.png', 'G8', 'W', 6, null),
       (248, 'Rook', 'B', 'GME87654OP', 'B_rook.png', 'H8', 'W', 6, null),
       (249, 'Pawn', 'B', 'GME87654OP', 'B_pawn.png', 'A7', 'W', 6, -1),
       (250, 'Pawn', 'B', 'GME87654OP', 'B_pawn.png', 'B7', 'W', 6, -1),
       (251, 'Pawn', 'B', 'GME87654OP', 'B_pawn.png', 'C7', 'W', 6, -1),
       (252, 'Pawn', 'B', 'GME87654OP', 'B_pawn.png', 'D7', 'W', 6, -1),
       (253, 'Pawn', 'B', 'GME87654OP', 'B_pawn.png', 'E7', 'W', 6, -1),
       (254, 'Pawn', 'B', 'GME87654OP', 'B_pawn.png', 'F7', 'W', 6, -1),
       (255, 'Pawn', 'B', 'GME87654OP', 'B_pawn.png', 'G7', 'W', 6, -1),
       (256, 'Pawn', 'B', 'GME87654OP', 'B_pawn.png', 'H7', 'W', 6, -1),
       (257, 'Rook', 'W', 'GME23456QR', 'W_rook.png', 'A1', 'B', 1, null),
       (258, 'Knight', 'W', 'GME23456QR', 'W_knight.png', 'B1', 'B', 1, null),
       (259, 'Bishop', 'W', 'GME23456QR', 'W_bishop.png', 'C1', 'B', 1, null),
       (260, 'Queen', 'W', 'GME23456QR', 'W_queen.png', 'D1', 'B', 1, null),
       (261, 'King', 'W', 'GME23456QR', 'W_king.png', 'E1', 'B', 1, null),
       (262, 'Bishop', 'W', 'GME23456QR', 'W_bishop.png', 'F1', 'B', 1, null),
       (263, 'Knight', 'W', 'GME23456QR', 'W_knight.png', 'G1', 'B', 1, null),
       (264, 'Rook', 'W', 'GME23456QR', 'W_rook.png', 'H1', 'B', 1, null),
       (265, 'Pawn', 'W', 'GME23456QR', 'W_pawn.png', 'A2', 'B', 1, 1),
       (266, 'Pawn', 'W', 'GME23456QR', 'W_pawn.png', 'B2', 'B', 1, 1),
       (267, 'Pawn', 'W', 'GME23456QR', 'W_pawn.png', 'C2', 'B', 1, 1),
       (268, 'Pawn', 'W', 'GME23456QR', 'W_pawn.png', 'D2', 'B', 1, 1),
       (269, 'Pawn', 'W', 'GME23456QR', 'W_pawn.png', 'E2', 'B', 1, 1),
       (270, 'Pawn', 'W', 'GME23456QR', 'W_pawn.png', 'F2', 'B', 1, 1),
       (271, 'Pawn', 'W', 'GME23456QR', 'W_pawn.png', 'G2', 'B', 1, 1),
       (272, 'Pawn', 'W', 'GME23456QR', 'W_pawn.png', 'H2', 'B', 1, 1),
       (273, 'Rook', 'B', 'GME23456QR', 'B_rook.png', 'A8', 'W', 8, null),
       (274, 'Knight', 'B', 'GME23456QR', 'B_knight.png', 'B8', 'W', 8, null),
       (275, 'Bishop', 'B', 'GME23456QR', 'B_bishop.png', 'C8', 'W', 8, null),
       (276, 'Queen', 'B', 'GME23456QR', 'B_queen.png', 'D8', 'W', 8, null),
       (277, 'King', 'B', 'GME23456QR', 'B_king.png', 'E8', 'W', 8, null),
       (278, 'Bishop', 'B', 'GME23456QR', 'B_bishop.png', 'F8', 'W', 8, null),
       (279, 'Knight', 'B', 'GME23456QR', 'B_knight.png', 'G8', 'W', 8, null),
       (280, 'Rook', 'B', 'GME23456QR', 'B_rook.png', 'H8', 'W', 8, null),
       (281, 'Pawn', 'B', 'GME23456QR', 'B_pawn.png', 'A7', 'W', 8, -1),
       (282, 'Pawn', 'B', 'GME23456QR', 'B_pawn.png', 'B7', 'W', 8, -1),
       (283, 'Pawn', 'B', 'GME23456QR', 'B_pawn.png', 'C7', 'W', 8, -1),
       (284, 'Pawn', 'B', 'GME23456QR', 'B_pawn.png', 'D7', 'W', 8, -1),
       (285, 'Pawn', 'B', 'GME23456QR', 'B_pawn.png', 'E7', 'W', 8, -1),
       (286, 'Pawn', 'B', 'GME23456QR', 'B_pawn.png', 'F7', 'W', 8, -1),
       (287, 'Pawn', 'B', 'GME23456QR', 'B_pawn.png', 'G7', 'W', 8, -1),
       (288, 'Pawn', 'B', 'GME23456QR', 'B_pawn.png', 'H7', 'W', 8, -1),
       (289, 'Rook', 'W', 'GME78901ST', 'W_rook.png', 'A1', 'B', 3, null),
       (290, 'Knight', 'W', 'GME78901ST', 'W_knight.png', 'B1', 'B', 3, null),
       (291, 'Bishop', 'W', 'GME78901ST', 'W_bishop.png', 'C1', 'B', 3, null),
       (292, 'Queen', 'W', 'GME78901ST', 'W_queen.png', 'D1', 'B', 3, null),
       (293, 'King', 'W', 'GME78901ST', 'W_king.png', 'E1', 'B', 3, null),
       (294, 'Bishop', 'W', 'GME78901ST', 'W_bishop.png', 'F1', 'B', 3, null),
       (295, 'Knight', 'W', 'GME78901ST', 'W_knight.png', 'G1', 'B', 3, null),
       (296, 'Rook', 'W', 'GME78901ST', 'W_rook.png', 'H1', 'B', 3, null),
       (297, 'Pawn', 'W', 'GME78901ST', 'W_pawn.png', 'A2', 'B', 3, 1),
       (298, 'Pawn', 'W', 'GME78901ST', 'W_pawn.png', 'B2', 'B', 3, 1),
       (299, 'Pawn', 'W', 'GME78901ST', 'W_pawn.png', 'C2', 'B', 3, 1),
       (300, 'Pawn', 'W', 'GME78901ST', 'W_pawn.png', 'D2', 'B', 3, 1),
       (301, 'Pawn', 'W', 'GME78901ST', 'W_pawn.png', 'E2', 'B', 3, 1),
       (302, 'Pawn', 'W', 'GME78901ST', 'W_pawn.png', 'F2', 'B', 3, 1),
       (303, 'Pawn', 'W', 'GME78901ST', 'W_pawn.png', 'G2', 'B', 3, 1),
       (304, 'Pawn', 'W', 'GME78901ST', 'W_pawn.png', 'H2', 'B', 3, 1),
       (305, 'Rook', 'B', 'GME78901ST', 'B_rook.png', 'A8', 'W', 10, null),
       (306, 'Knight', 'B', 'GME78901ST', 'B_knight.png', 'B8', 'W', 10, null),
       (307, 'Bishop', 'B', 'GME78901ST', 'B_bishop.png', 'C8', 'W', 10, null),
       (308, 'Queen', 'B', 'GME78901ST', 'B_queen.png', 'D8', 'W', 10, null),
       (309, 'King', 'B', 'GME78901ST', 'B_king.png', 'E8', 'W', 10, null),
       (310, 'Bishop', 'B', 'GME78901ST', 'B_bishop.png', 'F8', 'W', 10, null),
       (311, 'Knight', 'B', 'GME78901ST', 'B_knight.png', 'G8', 'W', 10, null),
       (312, 'Rook', 'B', 'GME78901ST', 'B_rook.png', 'H8', 'W', 10, null),
       (313, 'Pawn', 'B', 'GME78901ST', 'B_pawn.png', 'A7', 'W', 10, -1),
       (314, 'Pawn', 'B', 'GME78901ST', 'B_pawn.png', 'B7', 'W', 10, -1),
       (315, 'Pawn', 'B', 'GME78901ST', 'B_pawn.png', 'C7', 'W', 10, -1),
       (316, 'Pawn', 'B', 'GME78901ST', 'B_pawn.png', 'D7', 'W', 10, -1),
       (317, 'Pawn', 'B', 'GME78901ST', 'B_pawn.png', 'E7', 'W', 10, -1),
       (318, 'Pawn', 'B', 'GME78901ST', 'B_pawn.png', 'F7', 'W', 10, -1),
       (319, 'Pawn', 'B', 'GME78901ST', 'B_pawn.png', 'G7', 'W', 10, -1),
       (320, 'Pawn', 'B', 'GME78901ST', 'B_pawn.png', 'H7', 'W', 10, -1);