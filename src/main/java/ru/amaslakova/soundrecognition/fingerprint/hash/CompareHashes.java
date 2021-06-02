package ru.amaslakova.soundrecognition.fingerprint.hash;

import lombok.Data;
import ru.amaslakova.soundrecognition.fingerprint.AudioFile;
import ru.amaslakova.soundrecognition.fingerprint.hash.peak.HashedPeak;

import java.util.List;

@Data
public class CompareHashes {

    public double compare(AudioFile part, AudioFile full) {

        List<HashedPeak> partHashes = part.getFingerPrint().getHashes();
        List<HashedPeak> fullHashes = full.getFingerPrint().getHashes();

        int commonSize = partHashes.size() + fullHashes.size();
        partHashes.retainAll(fullHashes);
        int intersection = partHashes.size();

        return 1d / (commonSize - intersection) * intersection;
    }
}
