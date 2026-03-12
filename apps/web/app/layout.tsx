import type { Metadata } from 'next'
import Link from 'next/link'
import './globals.css'

export const metadata: Metadata = {
  title: 'OpenThread',
  description: 'Community-driven discussion platform MVP'
}

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang='en'>
      <body>
        <header className='border-b border-slate-200 bg-white'>
          <div className='shell flex items-center justify-between'>
            <Link href='/' className='text-lg font-bold text-ink'>
              OpenThread
            </Link>
            <nav className='flex items-center gap-4 text-sm'>
              <Link href='/' className='text-slate-700 hover:text-slate-900'>
                Home
              </Link>
              <Link href='/profile/11111111-1111-1111-1111-111111111111' className='text-slate-700 hover:text-slate-900'>
                Profile
              </Link>
            </nav>
          </div>
        </header>
        <main className='shell'>{children}</main>
      </body>
    </html>
  )
}
