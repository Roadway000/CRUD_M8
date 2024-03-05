package org.example.entity;

public class ProjectWorker {
    private int project_id;
    private int worker_id;

    public ProjectWorker(int project_id, int worker_id) {
        this.project_id = project_id;
        this.worker_id = worker_id;
    }
    @Override
    public String toString() {
        return "project_worker{"+"project_id="+project_id+", worker_id="+worker_id+'}';
    }
    public int getProject_id() { return project_id; }
    public void setProject_id(int project_id) { this.project_id = project_id; }
    public int getWorker_id() { return worker_id; }
    public void setWorker_id(int worker_id) { this.worker_id = worker_id; }
}
