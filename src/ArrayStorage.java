/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int numberOfResumes = 0;

    void clear() {
        for (int i = 0; i < numberOfResumes; i++) {
            storage[i] = null;
        }
        numberOfResumes = 0;
    }

    void save(Resume r) {
        storage[numberOfResumes] = r;
        numberOfResumes++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < numberOfResumes; i++) {
            if (storage[i].uuid == uuid) {
                return storage[i];
            }
        }
        System.out.println("uuid \"" + uuid + "\" wasn't found");
        return null;
    }

    void delete(String uuid) {
        int delitedIndex;
        for (int i = 0; i < numberOfResumes; i++) {
            if (storage[i].uuid == uuid) {
                delitedIndex = i;
                numberOfResumes--;
                for(int j=delitedIndex;j<numberOfResumes; j++){
                    storage[j]=storage[j+1];
                }
                storage[numberOfResumes]=null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResumes = new Resume[numberOfResumes];
        for (int i = 0; i < numberOfResumes; i++) {
            allResumes[i] = storage[i];
        }
        return allResumes;
    }

    int size() {
        return numberOfResumes;
    }
}
