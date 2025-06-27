package com.notes.app;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class NotesApp {
    private static final String FILE_NAME = "notes.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getChoice();

            switch (choice) {
                case 1 -> addNote();
                case 2 -> viewNotes();
                case 3 -> deleteAllNotes();
                case 4 -> searchNotes();
                case 5 -> System.out.println("Exiting Notes App. Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n========== Notes App ==========");
        System.out.println("1. Add Note");
        System.out.println("2. View All Notes");
        System.out.println("3. Delete All Notes");
        System.out.println("4. Search Notes");
        System.out.println("5. Exit");
        System.out.print("Select an option (1-5): ");
    }

    private static int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next(); // discard invalid input
        }
        return scanner.nextInt();
    }

    private static void addNote() {
        scanner.nextLine(); // clear buffer
        System.out.print("Enter your note: ");
        String note = scanner.nextLine();

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String noteEntry = String.format("[%s] %s", timestamp, note);

        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(noteEntry + System.lineSeparator());
            System.out.println("✅ Note saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving note: " + e.getMessage());
        }
    }

    private static void viewNotes() {
        System.out.println("\n🗒️  --- Your Notes ---");

        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No notes found. Start by adding a note.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean hasContent = false;
            while ((line = br.readLine()) != null) {
                System.out.println("• " + line);
                hasContent = true;
            }
            if (!hasContent) {
                System.out.println("(No notes yet.)");
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading notes: " + e.getMessage());
        }
    }

    private static void deleteAllNotes() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("📁 No notes file exists to delete.");
            return;
        }

        if (file.delete()) {
            System.out.println("🗑️  All notes deleted successfully.");
        } else {
            System.out.println("❌ Failed to delete notes.");
        }
    }

    private static void searchNotes() {
        scanner.nextLine(); // clear buffer
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine().toLowerCase();

        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No notes to search.");
            return;
        }

        boolean found = false;
        System.out.println("\n🔍 Search Results:");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(keyword)) {
                    System.out.println("• " + line);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No matching notes found.");
            }
        } catch (IOException e) {
            System.out.println("❌ Error during search: " + e.getMessage());
        }
    }
}

