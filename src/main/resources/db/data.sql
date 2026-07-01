-- ==========================================
-- USERS
-- Password for all users: admin@123
-- ==========================================

INSERT INTO users
(userName, email, password, phone, gender, dob, image)
VALUES
    ('Rahul Sharma',
     'rahul@gmail.com',
     '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2QDBG',
     '9876543210',
     'Male',
     '15-03-2000',
     'https://picsum.photos/200?random=1'),

    ('Priya Reddy',
     'priya@gmail.com',
     '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2QDBG',
     '9876543211',
     'Female',
     '20-08-1999',
     'https://picsum.photos/200?random=2'),

    ('Arjun Kumar',
     'arjun@gmail.com',
     '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2QDBG',
     '9876543212',
     'Male',
     '12-11-2001',
     'https://picsum.photos/200?random=3');



-- ==========================================
-- BARBERS
-- Password for all barbers: admin@123
-- ==========================================

INSERT INTO barbers
(name,email,password,image,about,available,slotsBooked)
VALUES

    ('John Barber',
     'john@barber.com',
     '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2QDBG',
     'https://picsum.photos/300?random=11',
     'Professional hair stylist with 8 years of experience.',
     true,
     '{}'),

    ('David Smith',
     'david@barber.com',
     '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2QDBG',
     'https://picsum.photos/300?random=12',
     'Expert in beard styling and hair cutting.',
     true,
     '{}'),

    ('Michael Brown',
     'michael@barber.com',
     '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2QDBG',
     'https://picsum.photos/300?random=13',
     'Fade specialist with modern styling techniques.',
     true,
     '{}');



-- ==========================================
-- APPOINTMENTS
-- ==========================================

INSERT INTO appointments
(userId,
 barberId,
 slotDate,
 slotTime,
 userData,
 barberData,
 cancelled,
 isCompleted,
 message)
VALUES

    (
        1,
        1,
        '2026-07-01',
        '10:00 AM',
        JSON_OBJECT(
                'id',1,
                'userName','Rahul Sharma',
                'email','rahul@gmail.com',
                'phone','9876543210'
        ),
        JSON_OBJECT(
                'id',1,
                'name','John Barber',
                'email','john@barber.com'
        ),
        false,
        false,
        'Regular Haircut'
    ),

    (
        2,
        2,
        '2026-07-01',
        '11:00 AM',
        JSON_OBJECT(
                'id',2,
                'userName','Priya Reddy',
                'email','priya@gmail.com',
                'phone','9876543211'
        ),
        JSON_OBJECT(
                'id',2,
                'name','David Smith',
                'email','david@barber.com'
        ),
        false,
        false,
        'Hair Spa'
    ),

    (
        3,
        3,
        '2026-07-02',
        '05:30 PM',
        JSON_OBJECT(
                'id',3,
                'userName','Arjun Kumar',
                'email','arjun@gmail.com',
                'phone','9876543212'
        ),
        JSON_OBJECT(
                'id',3,
                'name','Michael Brown',
                'email','michael@barber.com'
        ),
        false,
        false,
        'Beard Trim'
    );