import { getPost, getPostComments } from '@/lib/api'

type Props = {
  params: Promise<{ id: string }>
}

export default async function PostPage({ params }: Props) {
  const { id } = await params
  const [post, comments] = await Promise.all([getPost(id), getPostComments(id)])

  return (
    <div className='space-y-6'>
      <article className='card'>
        <div className='mb-2 text-xs text-slate-500'>Score {post.score}</div>
        <h1 className='text-2xl font-bold text-ink'>{post.title}</h1>
        <p className='mt-3 text-sm text-slate-700'>{post.body}</p>
      </article>

      <section className='space-y-3'>
        <h2 className='text-xl font-semibold text-ink'>Comments</h2>
        {comments.data.length === 0 ? (
          <div className='card text-sm text-slate-500'>No comments yet.</div>
        ) : (
          comments.data.map((comment) => (
            <div key={comment.id} className='card' style={{ marginLeft: `${comment.depth * 12}px` }}>
              <div className='mb-2 text-xs text-slate-500'>Depth {comment.depth}</div>
              <p className='text-sm text-slate-700'>{comment.body}</p>
            </div>
          ))
        )}
      </section>
    </div>
  )
}
