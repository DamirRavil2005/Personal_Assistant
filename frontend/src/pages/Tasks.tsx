import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";

interface Task {
  id: number;
  title: string;
  description?: string;
  status: string;
  priority: string;
  dueDate?: string;
  createdAt: string;
}

export default function Tasks() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [title, setTitle] = useState("");
  const [priority, setPriority] = useState("MEDIUM");
  const navigate = useNavigate();
  const username = localStorage.getItem("username");

  const load = async () => {
    const { data } = await api.get("/tasks");
    setTasks(data.content);
  };

  useEffect(() => {
    const timer = setTimeout(() => { void load(); }, 0);
    return () => clearTimeout(timer);
  }, []);

  const createTask = async (e: React.FormEvent) => {
    e.preventDefault();
    await api.post("/tasks", { title, priority, status: "TODO" });
    setTitle("");
    setPriority("MEDIUM");
    load();
  };

  const updateStatus = async (task: Task, status: string) => {
    await api.put(`/tasks/${task.id}`, {
      title: task.title,
      description: task.description,
      status,
      priority: task.priority,
      dueDate: task.dueDate,
    });
    load();
  };

  const deleteTask = async (id: number) => {
    await api.delete(`/tasks/${id}`);
    load();
  };

  const logout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const priorityColor: Record<string, string> = {
    LOW: "bg-gray-200",
    MEDIUM: "bg-blue-200",
    HIGH: "bg-orange-200",
    URGENT: "bg-red-200",
  };
  const statusColor: Record<string, string> = {
    TODO: "text-gray-600",
    IN_PROGRESS: "text-blue-600",
    DONE: "text-green-600",
    CANCELLED: "text-red-600",
  };

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <div className="max-w-4xl mx-auto">
        <header className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold">Мои задачи</h1>
          <div className="flex items-center gap-4">
            <span className="text-gray-700">Привет, {username}!</span>
            <button onClick={logout} className="text-red-600 hover:underline">
              Выйти
            </button>
          </div>
        </header>

        <form onSubmit={createTask} className="bg-white p-4 rounded-xl shadow mb-6 flex gap-2">
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder="Новая задача..."
            className="flex-1 p-2 border rounded"
            required
          />
          <select
            value={priority}
            onChange={(e) => setPriority(e.target.value)}
            className="p-2 border rounded"
          >
            <option value="LOW">Низкий</option>
            <option value="MEDIUM">Средний</option>
            <option value="HIGH">Высокий</option>
            <option value="URGENT">Срочно</option>
          </select>
          <button type="submit" className="bg-blue-600 text-white px-4 rounded hover:bg-blue-700">
            Добавить
          </button>
        </form>

        <div className="space-y-2">
          {tasks.length === 0 && (
            <div className="text-center text-gray-500 p-8 bg-white rounded-xl">
              Задач пока нет
            </div>
          )}
          {tasks.map((t) => (
            <div
              key={t.id}
              className="bg-white p-4 rounded-xl shadow flex items-center justify-between"
            >
              <div className="flex-1">
                <div className="flex items-center gap-2">
                  <span className={`text-xs px-2 py-1 rounded ${priorityColor[t.priority]}`}>
                    {t.priority}
                  </span>
                  <span className={`text-sm font-semibold ${statusColor[t.status]}`}>
                    {t.status}
                  </span>
                </div>
                <h3 className="font-medium mt-1">{t.title}</h3>
                {t.description && <p className="text-sm text-gray-600">{t.description}</p>}
              </div>
              <div className="flex gap-2">
                <select
                  value={t.status}
                  onChange={(e) => updateStatus(t, e.target.value)}
                  className="p-1 border rounded text-sm"
                >
                  <option value="TODO">TODO</option>
                  <option value="IN_PROGRESS">IN_PROGRESS</option>
                  <option value="DONE">DONE</option>
                  <option value="CANCELLED">CANCELLED</option>
                </select>
                <button
                  onClick={() => deleteTask(t.id)}
                  className="text-red-600 hover:underline text-sm"
                >
                  Удалить
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}