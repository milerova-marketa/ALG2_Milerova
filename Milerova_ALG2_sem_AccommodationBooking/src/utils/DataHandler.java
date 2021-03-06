/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import app.Client;
import app.Property;
import app.Reservation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Třída pro zpracování dat v souborech.
 *
 * @author Marketa.Milerova
 */
public class DataHandler {

    /**
     * Seznam klientů
     */
    protected static List<Client> clients = new ArrayList();

    /**
     * Seznam rezervací
     */
    protected static List<Reservation> reservations = new ArrayList();

    /**
     * Seznam objektů
     */
    protected static List<Property> properties = new ArrayList();

    /**
     * Metoda vrátí aktualizované seznamy o data ze souborů
     *
     * @param clients seznam klientů
     * @param properties seznam objektů
     * @param reservations seznam rezervací
     * @param type typ načítaných souborů
     * @return log o chybách v načítání
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public static String updateList(List<Client> clients, List<Property> properties, List<Reservation> reservations, String type) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DataHandler.clients = clients;
        DataHandler.reservations = reservations;
        DataHandler.properties = properties;
        if (!type.isEmpty()) {
            String logC = loadListFromFile("client", type);
            if (!logC.isEmpty()) {
                logC = "ClientInfo:\n" + logC;
            }
            String logP = loadListFromFile("property", type);
            if (!logP.isEmpty()) {
                logP = "PropertyInfo:\n" + logP;
            }
            String logR = loadListFromFile("reservation", type);
            if (!logR.isEmpty()) {
                logR = "ReservationInfo:\n" + logR;
            }
            return logC + logP + logR;
        }
        return "";
    }

    /**
     * Metoda kontroluje, zda se již člen v seznamu nenachází.
     *
     * @param list seznam
     * @param p kontrolovaný člen
     * @return true pokud se člen v seznamu již nachází. Jinak false.
     */
    public static boolean checkDuplicate(List list, Object p) {
        if (list.stream().anyMatch((o) -> (o.equals(p)))) {
            return true;
        }
        return false;
    }

    /**
     * Metoda kontrolující, zda se klient a objekt nachází v seznamu
     *
     * @param firstName křestníjméno klienta
     * @param lastName příjmení klienta
     * @param propertyName název objektu
     * @return list ve kterém se nachází nalezený klient a objekt
     */
    public static List<Object> checkValidity(String firstName, String lastName, String propertyName) {
        boolean found = false;
        boolean found1 = false;
        int i, j = -1;
        for (i = 0; i < clients.size(); i++) {
            if (clients.get(i).getFirstName().equals(firstName) && clients.get(i).getLastName().equals(lastName)) {
                found = true;
                break;
            }
        }
        if (found) {
            for (j = 0; j < properties.size(); j++) {
                if (properties.get(j).getName().equals(propertyName)) {
                    found1 = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException(firstName + " " + lastName + " neexistuje v databázi");
        }
        if (!found1) {
            throw new IllegalArgumentException(propertyName + " neexistuje v databázi");
        }
        List<Object> clientObject = new ArrayList();
        clientObject.add(clients.get(i));
        clientObject.add(properties.get(j));
        return clientObject;
    }

    // Metoda načítá jednolivé vstupy ze souborů, vytváří z nich objekty a přidává je do seznamů
    private static String loadListFromFile(String name, String type) {
        List<String> info;
        String fileName = Formater.firstLetterToUpperCase(name)
                + "Info" + type;
        try {
            info = FileHandler.readFile(fileName);
            String log = "";
            switch (name) {
                case "client":
                    Client c;
                    for (String string : info) {
                        try {
                            c = Parser.parseClient(string);
                            if (!checkDuplicate(clients, c)) {
                                clients.add(c);
                            } else {
                                throw new ObjectAlreadyInListException("Klient " + c.getFirstName() + " " + c.getLastName() + " již existuje");
                            }
                        } catch (RuntimeException | ObjectAlreadyInListException e) {
                            log = log + e.getMessage() + "\n";
                        }
                    }
                    break;
                case "property":
                    Property p;
                    for (String string : info) {
                        try {
                            p = Parser.parseProperty(string);
                            if (!checkDuplicate(properties, p)) {
                                properties.add(p);
                            } else {
                                throw new ObjectAlreadyInListException("Objekt " + p.getName() + " již existuje");
                            }
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | RuntimeException | ObjectAlreadyInListException e) {
                            log = log + e.getMessage() + "\n";
                        }
                    }
                    break;
                case "reservation":
                    Reservation r;
                    for (String string : info) {
                        try {
                            r = Parser.parseReservation(string);
                            reservations.add(r);
                        } catch (Exception e) {
                            log = log + e.getMessage() + "\n";
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Seznam nenalezen");
            }
            return log;
        } catch (IOException e) {
            return "File not found";
        }
    }

}
