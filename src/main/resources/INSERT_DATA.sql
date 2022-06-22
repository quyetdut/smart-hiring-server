INSERT INTO roles(name) VALUES('ROLE_DEV');
INSERT INTO roles(name) VALUES('ROLE_PO');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO `position` VALUES
                           (1,'https://everlifeai.github.io/docs/images/developer.png','Web Developer','DEV'),
                           (2,'https://cdn.dribbble.com/users/1428228/screenshots/3454859/designer.png','Back-End Developer','DEV'),
                           (3,'https://cdn.computercareers.org/wp-content/uploads/Solution-Architect.png','Full-stack Developer','DEV'),
                           (4,'https://cdn.computercareers.org/wp-content/uploads/Solution-Architect.png','Quality Assurance','DEV'),
                           (5,'https://cdn.computercareers.org/wp-content/uploads/Solution-Architect.png','Solution Architect','DEV'),
                           (6,'https://cdn.computercareers.org/wp-content/uploads/Solution-Architect.png','Designer','DEV'),
                           (7,'https://cdn.computercareers.org/wp-content/uploads/Solution-Architect.png','Product Owner','PO'),
                           (8,'https://cdn.computercareers.org/wp-content/uploads/Solution-Architect.png','Scrum Master','PO'),
                           (9,'https://cdn.computercareers.org/wp-content/uploads/Solution-Architect.png','Project Management','PO');


INSERT INTO `capabilities` VALUES
                               (1,'IOS'),
                               (2,'Java'),
                               (3,'NodeJs'),
                               (4,'PHP'),
                               (5, 'Python'),
                               (6, 'ReactJs'),
                               (7, 'React Native'),
                               (8, 'VueJs'),
                               (9, 'Unity'),
                               (10, 'Dot Net'),
                               (11, 'Game Artist'),
                               (12, 'Visual Design'),
                               (13, 'Manual Tester'),
                               (14, 'Automation Tester');

INSERT INTO  `position_capabilities` VALUES
                                         (1, 1, 1),
                                         (2, 2, 1),
                                         (3, 3, 1),
                                         (4, 4, 1),
                                         (5, 5, 1),
                                         (6, 6, 1),
                                         (7, 7, 1),
                                         (8, 8, 1),
                                         (9, 9, 1),
                                         (10, 10, 1),
                                         (11, 11, 1),
                                         (12, 12, 1),
                                         (13, 13, 1),
                                         (14, 14, 1),
                                         (15, 1, 2),
                                         (16, 2, 2),
                                         (17, 3, 2),
                                         (18, 4, 2),
                                         (19, 5, 2),
                                         (20, 6, 2),
                                         (21, 7, 2),
                                         (22, 8, 2),
                                         (23, 9, 2),
                                         (24, 10, 2),
                                         (25, 11, 2),
                                         (26, 12, 2),
                                         (27, 13, 2),
                                         (28, 14, 2),
                                         (29, 1, 3),
                                         (30, 2, 3),
                                         (31, 3, 3),
                                         (32, 4, 3),
                                         (33, 5, 3),
                                         (34, 6, 3),
                                         (35, 7, 3),
                                         (36, 8, 3),
                                         (37, 9, 3),
                                         (38, 10, 3),
                                         (39, 11, 3),
                                         (40, 12, 3),
                                         (41, 13, 3),
                                         (42, 14, 3),
                                         (43, 13, 4),
                                         (44, 14, 4),
                                         (45, 1, 5),
                                         (46, 2, 5),
                                         (47, 3, 5),
                                         (48, 4, 5),
                                         (49, 5, 5),
                                         (50, 6, 5),
                                         (51, 7, 5),
                                         (52, 8, 5),
                                         (53, 9, 5),
                                         (54, 10, 5),
                                         (55, 11, 5),
                                         (56, 12, 5),
                                         (57, 13, 5),
                                         (58, 14, 5),
                                         (59, 11, 6),
                                         (60, 12, 6);


INSERT INTO division ( name ) VALUES ('Russia');
INSERT INTO division ( name ) VALUES ('France');
INSERT INTO division ( name ) VALUES ('Switzerland');
INSERT INTO division ( name ) VALUES ('Germany');
INSERT INTO division ( name ) VALUES ('England');
INSERT INTO division ( name ) VALUES ('Vietnam');

INSERT INTO awards( name ,img) VALUES ('Taca Iberica Rugby', 'https://i.pinimg.com/236x/8c/ae/34/8cae34a020ea258fce4907e9ba8d6c94.jpg');
INSERT INTO awards( name ,img) VALUES ('OLC', 'https://olc-wordpress-assets.s3.amazonaws.com/uploads/2021/04/OLC-Awards-Thumbnail-1200x800.jpg');
INSERT INTO awards( name ,img) VALUES ('REC', 'https://d16yj43vx3i1f6.cloudfront.net/uploads/2019/06/Award-cup-getty-image.jpg');

INSERT INTO locations( city ,time_zone) VALUES ('Da Nang', 'GMT +2');
INSERT INTO locations( city ,time_zone) VALUES ('Ha Noi', 'GMT +2');
INSERT INTO locations( city ,time_zone) VALUES ('Ho Chi Minh', 'GMT +2');


Insert Into process(id, process_name) values
                                          (1,"Agile development methodology"),
                                          (2,"DevOps deployment methodology"),
                                          (3,"Waterfall development method"),
                                          (4,"Rapid application development");