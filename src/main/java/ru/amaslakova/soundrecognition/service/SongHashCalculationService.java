package ru.amaslakova.soundrecognition.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.amaslakova.soundrecognition.domain.model.Song;
import ru.amaslakova.soundrecognition.domain.model.SongHash;
import ru.amaslakova.soundrecognition.fingerprint.AudioFile;
import ru.amaslakova.soundrecognition.fingerprint.hash.FingerPrint;
import ru.amaslakova.soundrecognition.fingerprint.hash.peak.HashedPeak;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service for SongHash.
 */
@Service
@RequiredArgsConstructor
public class SongHashCalculationService {


    public Set<SongHash> calculateSongHashes(Song song) throws Exception {

        File file = new File(song.getPath());
        AudioFile audioFile = new AudioFile(file);
        FingerPrint fingerPrint = new FingerPrint(audioFile);
        Set<SongHash> hashes = new HashSet<>();
        long start = System.currentTimeMillis();
        List<HashedPeak> hashedPeaks = fingerPrint.getHashes();

        for (HashedPeak peak : hashedPeaks) {
            String hash = peak.getHashAsHex();
            SongHash newHash = new SongHash();
            newHash.setHash(hash);
            hashes.add(newHash);
        }
        long end = System.currentTimeMillis();
        System.out.println("Time to fingerprint: " + (end - start));
        System.out.println("Hash size: " + hashes.size());

        return hashes;
    }

}
