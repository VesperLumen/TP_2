package DAO;

import Model.Holiday;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CongeDAOImpl implements GenericDAOI<Holiday> {
    private Connection conn;

    public CongeDAOImpl() {
        this.conn = DBConnection.getConnexion();
    }


    @Override
    public List<Holiday> findAll() {
        List<Holiday> conge = new ArrayList<>();
        String query = "SELECT * FROM conge";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                conge.add(new Holiday(
                        rs.getInt("id"),
                        rs.getString("date_debut"),
                        rs.getString("date_fin"),
                        Holiday.Types.valueOf(rs.getString("type"))
                ));

            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de tous les congés !!!!!");
        }
        return conge;
    }


    @Override
    public void add(Holiday holiday) {

        String Query = "INSERT INTO Conge(date_debut, date_fin, type,id_empl) VALUES(?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(Query)) {

            stmt.setString(1, holiday.getDate_debut());
            stmt.setString(2, holiday.getDate_fin());
            stmt.setString(3, holiday.getTypes().name());
            stmt.setInt(4, holiday.getId_empl());

            stmt.executeUpdate();

            System.out.println("Congé ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de Congé !");
            //e.printStackTrace();

        }
    }

    @Override
    public void update(Holiday holiday, int id_empl) {
        String query = "UPDATE conge SET id = ?, date_debut = ?, date_fin = ?, type = ? WHERE id_empl = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, holiday.getId());
            stmt.setString(2, holiday.getDate_debut());
            stmt.setString(3, holiday.getDate_fin());
            stmt.setString(4, holiday.getTypes().name());
            stmt.setInt(6, id_empl);
            stmt.executeUpdate();

            System.out.println("Conge modifier avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modefication de Conge !!!!!");
        }
    }


    public void delete(int id_empl) {
        String query = "DELETE FROM conge WHERE id_empl = ?";
         try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, id_empl);
        stmt.executeUpdate();
        System.out.println("conge supprimé avec succès !");
    } catch (SQLException e) {
        System.out.println("Erreur lors de la suppression de conge !!!!");
      }
    }
}


