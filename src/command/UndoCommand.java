package command;

public interface UndoCommand extends Command{

    /**
     * undo操作
     */
    void undo();

}
