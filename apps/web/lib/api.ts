export type Pagination = {
  page: number
  per_page: number
  total: number
}

export type Paginated<T> = {
  data: T[]
  pagination: Pagination
}

export type Community = {
  id: string
  name: string
  slug: string
  description: string | null
  created_at: string
}

export type Post = {
  id: string
  community_id: string
  user_id: string
  title: string
  body: string
  score: number
  comment_count: number
  created_at: string
}

export type Comment = {
  id: string
  post_id: string
  user_id: string
  parent_id: string | null
  depth: number
  body: string
  score: number
  created_at: string
}

export type User = {
  id: string
  name: string
  username: string
  avatar_url: string | null
  bio: string | null
}

export type ActivityItem = {
  type: 'post' | 'comment' | 'vote'
  payload: Record<string, unknown>
  created_at: string
}

const API_BASE = process.env.API_BASE_URL ?? process.env.NEXT_PUBLIC_API_BASE_URL ?? 'http://localhost:8080/api/v1'

async function request<T>(path: string): Promise<T> {
  const response = await fetch(`${API_BASE}${path}`, {
    cache: 'no-store'
  })

  if (!response.ok) {
    const text = await response.text()
    throw new Error(`API error ${response.status}: ${text}`)
  }

  return response.json() as Promise<T>
}

export function getFeed(): Promise<Paginated<Post>> {
  return request<Paginated<Post>>('/feed?page=1&per_page=20&sort=hot')
}

export function getCommunities(): Promise<Paginated<Community>> {
  return request<Paginated<Community>>('/communities?page=1&per_page=20')
}

export function getCommunity(id: string): Promise<Community> {
  return request<Community>(`/communities/${id}`)
}

export function getPost(id: string): Promise<Post> {
  return request<Post>(`/posts/${id}`)
}

export function getPostComments(id: string): Promise<Paginated<Comment>> {
  return request<Paginated<Comment>>(`/posts/${id}/comments?page=1&per_page=20`)
}

export function getUser(id: string): Promise<User> {
  return request<User>(`/users/${id}`)
}

export function getUserActivity(id: string): Promise<Paginated<ActivityItem>> {
  return request<Paginated<ActivityItem>>(`/users/${id}/activity?page=1&per_page=20`)
}
