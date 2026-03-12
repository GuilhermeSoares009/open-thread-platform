import Link from 'next/link'
import { getCommunities, getFeed } from '@/lib/api'

export default async function HomePage() {
  const [communities, feed] = await Promise.all([getCommunities(), getFeed()])

  return (
    <div className='grid gap-6 lg:grid-cols-[280px_1fr]'>
      <aside className='card space-y-3'>
        <h2 className='text-sm font-semibold uppercase tracking-wide text-slate-500'>Communities</h2>
        <ul className='space-y-2'>
          {communities.data.map((community) => (
            <li key={community.id}>
              <Link href={`/community/${community.id}`} className='font-medium text-ink hover:text-accent'>
                {community.name}
              </Link>
              <p className='text-xs text-slate-600'>{community.description ?? 'No description yet'}</p>
            </li>
          ))}
        </ul>
      </aside>

      <section className='space-y-4'>
        <h1 className='text-2xl font-bold text-ink'>Ranked Feed</h1>
        {feed.data.length === 0 ? (
          <div className='card text-sm text-slate-500'>No posts yet.</div>
        ) : (
          feed.data.map((post) => (
            <article key={post.id} className='card'>
              <div className='mb-2 text-xs text-slate-500'>Score {post.score} · {post.comment_count} comments</div>
              <h2 className='text-lg font-semibold text-ink'>
                <Link href={`/post/${post.id}`} className='hover:text-accent'>
                  {post.title}
                </Link>
              </h2>
              <p className='mt-2 text-sm text-slate-700'>{post.body}</p>
            </article>
          ))
        )}
      </section>
    </div>
  )
}
