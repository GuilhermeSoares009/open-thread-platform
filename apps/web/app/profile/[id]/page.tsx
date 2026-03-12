import { getUser, getUserActivity } from '@/lib/api'

type Props = {
  params: Promise<{ id: string }>
}

export default async function ProfilePage({ params }: Props) {
  const { id } = await params
  const [user, activity] = await Promise.all([getUser(id), getUserActivity(id)])

  return (
    <div className='space-y-6'>
      <section className='card'>
        <h1 className='text-2xl font-bold text-ink'>{user.name}</h1>
        <p className='text-sm text-slate-600'>@{user.username}</p>
        <p className='mt-2 text-sm text-slate-700'>{user.bio ?? 'No bio yet.'}</p>
      </section>

      <section className='space-y-3'>
        <h2 className='text-xl font-semibold text-ink'>Recent Activity</h2>
        {activity.data.length === 0 ? (
          <div className='card text-sm text-slate-500'>No activity yet.</div>
        ) : (
          activity.data.map((item, index) => (
            <div key={`${item.type}-${index}`} className='card'>
              <div className='text-xs uppercase tracking-wide text-slate-500'>{item.type}</div>
              <pre className='mt-2 overflow-x-auto rounded bg-slate-900 p-3 text-xs text-slate-100'>
                {JSON.stringify(item.payload, null, 2)}
              </pre>
            </div>
          ))
        )}
      </section>
    </div>
  )
}
