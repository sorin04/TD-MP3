package fr.btsciel.td_mp3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Gestion_MP3 {
    private TagMP3 tag;

    public TagMP3 getTag() {
        return tag;
    }

    private Path fichierSource;
    private byte[]tab;
    public Gestion_MP3(Path fichierSource) {
        tag = new TagMP3();
        this.fichierSource = fichierSource;
        tab = new byte[128];


    }
    public void lireTags() throws IOException {
        InputStream is = new FileInputStream(fichierSource.toString());
        try (DataInputStream dis = new DataInputStream(is)) {
            long fileSize = Files.size(fichierSource);
            dis.skipBytes((int) (fileSize - 128));
            dis.read(tab);
        }

        String titre = new String(tab, 3, 30).trim();
        String artiste = new String(tab, 33, 30).trim();
        String album = new String(tab, 63, 30).trim();
        String annee = new String(tab, 93, 4).trim();
        String commentaire = new String(tab, 97, 28).trim();
        byte track = tab[126];
        byte genre = tab[127];


        tag.setTitre(titre);
        tag.setArtiste(artiste);
        tag.setAlbum(album);
        tag.setAnnee(annee);
        tag.setCommentaire(commentaire);
        tag.setTrack(track);
        tag.setGenre(genre);
    }

    public void enregistrerTags() throws IOException {
        for (int i = 0; i < tab.length; i++) {
            tab[i]=(byte)0x00;
        }
        for (int i = 0; i < tag.getTitre().length(); i++) {
            tab[3+i]=(byte)tag.getTitre().charAt(i);
        }
        for (int i = 0; i < tag.getArtiste().length(); i++) {
            tab[33+i]=(byte)tag.getArtiste().charAt(i);
        }
        for (int i = 0; i < tag.getAlbum().length(); i++) {
            tab[63+i]=(byte)tag.getAlbum().charAt(i);
        }
        for (int i = 0; i < tag.getAnnee().length(); i++) {
            tab[93+i]=(byte)tag.getAnnee().charAt(i);
        }
        for (int i = 0; i < tag.getCommentaire().length(); i++) {
            tab[97+i]=(byte)tag.getCommentaire().charAt(i);
        }
        tab[126] = tag.getTrack();
        tab[127] = tag.getGenre();
        RandomAccessFile randomAccessFile = new RandomAccessFile(fichierSource.toAbsolutePath().toString(),"rw");
        long taille = randomAccessFile.length();
        randomAccessFile.seek(taille-128);
        randomAccessFile.write(tab);
        randomAccessFile.close();
    }
    public void effacerTags() {
        tag.setTitre("");
        tag.setArtiste("");
        tag.setAlbum("");
        tag.setAnnee("");
        tag.setCommentaire("");
        tag.setTrack((byte) 0);
        tag.setGenre((byte) 0);
    }

}
