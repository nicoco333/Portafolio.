import { api } from "./api";

const getAllByUser = (userId) => api.get("/posts", { params: { userId } });
const create = (post) => api.post("/posts", post);
const update = (id, post) => api.put(`/posts/${id}`, post);
const remove = (id) => api.delete(`/posts/${id}`);

export const postService = {
  getAllByUser,
  create,
  update,
  remove
};
