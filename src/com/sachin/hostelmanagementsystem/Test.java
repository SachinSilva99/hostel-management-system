package com.sachin.hostelmanagementsystem;

public /* Author: Sachin */
class
Test {
    public static void main(String[] args) {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
/*        Student student = new Student(
                "S001", "sachin", "beruwala",
                "077", new Date(1999, 7, 14),
                GENDER.MALE);

        Room room = new Room("R001", ROOM_TYPE.AC, 20000, 12);

        Reservation reservation = new Reservation("reservation", new Date(), STATUS.COMPLETED, student, room);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        student.setReservations(reservations);
        room.setReservations(reservations);
        session.save(student);
        session.save(room);
//        session.save(reservation);*/
//        Student s001 = session.load(Student.class, "S001");
//        session.delete(s001);
//        transaction.commit();
       /* String currentResId = "RS009";
        String[] split = currentResId.split("RS00");
        for(String a : split){
            System.out.println(a);
        }
        int id = Integer.parseInt(split[1]);
        id += 1;
       String result = "RS00" + id;
        System.out.println(result);*/
        /*ArrayList<String> list = new ArrayList<>();
        list.add("Sachin");
        list.add("Saman");
        list.add("Sanath");
        List<String> s1 = list.stream().filter(s -> s.contains("") || s.contains("")).collect(Collectors.toList());
        System.out.println(s1);*/
        String input = "+94779672493"; // replace with your input
        String regex = "^(?!(?:\\+947|07)7(?!1234))[07]\\d{8}$|^(?!(?:\\+947|07)71234567)[+]?947\\d{7}$";

        if (input.matches(regex)) {
            System.out.println("Valid");
        } else {
            System.out.println("Invalid");
        }
    }
}
