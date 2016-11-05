package seedu.todo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.model.ReadOnlyToDoList;

/**
 * A class to access ToDoList data stored as an xml file on the hard disk.
 */
public class XmlToDoListStorage implements ToDoListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlToDoListStorage.class);

    private String filePath;

    public XmlToDoListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getToDoListFilePath(){
        return filePath;
    }
    
    public void setToDoListFilePath(String filepath){
        this.filePath = filepath;
    }

    /**
     * Similar to {@link #readToDoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyToDoList> readToDoList(String filePath) 
            throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File toDoListOptional = new File(filePath);

        if (!toDoListOptional.exists()) {
            logger.info("ToDoList file "  + toDoListOptional + " not found");
            return Optional.empty();
        }

        ReadOnlyToDoList toDoList = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        return Optional.of(toDoList);
    }

    /**
     * Similar to {@link #saveToDoList(ReadOnlyToDoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException {
        assert toDoList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableToDoList(toDoList));
    }

    @Override
    public Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException {
        return readToDoList(filePath);
    }

    @Override
    public void saveToDoList(ReadOnlyToDoList toDoList) throws IOException {
        saveToDoList(toDoList, filePath);
    }
    
    @Override
    public void updateToDoListFilePath(String filepath, ReadOnlyToDoList toDoList) throws IOException {
        this.setToDoListFilePath(filepath);
        this.saveToDoList(toDoList);
    }
}
