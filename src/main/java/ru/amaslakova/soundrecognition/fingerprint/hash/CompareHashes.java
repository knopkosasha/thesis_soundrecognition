package ru.amaslakova.soundrecognition.fingerprint.hash;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.amaslakova.soundrecognition.fingerprint.AudioFile;
import ru.amaslakova.soundrecognition.fingerprint.hash.peak.HashedPeak;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Service
public class CompareHashes {

    public double compare(AudioFile part, AudioFile full) {

        Long start = System.currentTimeMillis();
        List<HashedPeak> partHashes = part.getFingerPrint().getHashes();
        Set<String> partHexes = new HashSet<>();
        Set<String> fullHexes = new HashSet<>();
        for (HashedPeak hashedPeak : partHashes) {
            partHexes.add(hashedPeak.getHashAsHex());
        }
        List<HashedPeak> fullHashes = full.getFingerPrint().getHashes();
        for (HashedPeak hashedPeak : fullHashes) {
            fullHexes.add(hashedPeak.getHashAsHex());
        }
        int partSize = partHexes.size();

        int commonSize = partHexes.size() + fullHexes.size();
        partHexes.retainAll(fullHexes);
        int intersection = partHexes.size();

        Long end = System.currentTimeMillis();
        Long timing = end - start;
        System.out.println("Time to find a match: " + timing);


        return 1d / partSize * intersection;
    }
}
