import { getCommunity, getFeed } from '@/lib/api'

type Props = {
  params: Promise<{ id: string }>
}

export default async function CommunityPage({ params }: Props) {
  const { id } = await params
  const [community, feed] = await Promise.all([getCommunity(id), getFeed()])
  const communityPosts = feed.data.filter((post) => post.community_id === id)

  return (
    <div className='space-y-6'>
      <section className='card'>
        <h1 className='text-2xl font-bold text-ink'>{community.name}</h1>
        <p className='mt-2 text-sm text-slate-700'>{community.description ?? 'No description.'}</p>
      </section>

      <section className='space-y-3'>
        <h2 className='text-xl font-semibold text-ink'>Posts</h2>
        {communityPosts.length === 0 ? (
          <div className='card text-sm text-slate-500'>No posts for this community yet.</div>
        ) : (
          communityPosts.map((post) => (
            <article key={post.id} className='card'>
              <h3 className='font-semibold text-ink'>{post.title}</h3>
              <p className='mt-2 text-sm text-slate-700'>{post.body}</p>
            </article>
          ))
        )}
      </section>
    </div>
  )
}
