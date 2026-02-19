import { spawn } from 'node:child_process'
import { readFileSync } from 'node:fs'
import { dirname, resolve } from 'node:path'
import { fileURLToPath } from 'node:url'

const scriptDir = dirname(fileURLToPath(import.meta.url))
const repoRoot = resolve(scriptDir, '..')
const envPath = resolve(repoRoot, '.env')

const parseEnvFile = (content) => {
  const entries = {}

  content.split(/\r?\n/).forEach((rawLine) => {
    const line = rawLine.trim()
    if (!line || line.startsWith('#')) return

    const equalsIndex = line.indexOf('=')
    if (equalsIndex === -1) return

    const rawKey = line.slice(0, equalsIndex).trim()
    const key = rawKey.startsWith('export ') ? rawKey.slice(7).trim() : rawKey
    let value = line.slice(equalsIndex + 1).trim()

    if (
      (value.startsWith('"') && value.endsWith('"')) ||
      (value.startsWith("'") && value.endsWith("'"))
    ) {
      value = value.slice(1, -1)
    }

    entries[key] = value
  })

  return entries
}

const env = { ...process.env }

try {
  const fileContent = readFileSync(envPath, 'utf8')
  const fileEntries = parseEnvFile(fileContent)
  Object.assign(env, fileEntries)
} catch {
  // If .env is missing, rely on process.env
}

const figmaToken = env.FIGMA_API_KEY || env.FIGMA_TOKEN

if (!figmaToken) {
  console.error('Missing FIGMA_TOKEN or FIGMA_API_KEY in .env or environment')
  process.exit(1)
}

env.FIGMA_API_KEY = figmaToken

const npxCandidates = [
  env.NPX_CMD,
  env.NPX_PATH,
  'npx',
  'C:\\nvm4w\\nodejs\\npx.cmd'
].filter(Boolean)

const spawnNpx = (index = 0) => {
  const command = npxCandidates[index]

  if (!command) {
    console.error('Failed to locate npx executable')
    process.exit(1)
  }

  const args = ['-y', 'figma-mcp']
  const spawnOptions = { stdio: 'inherit', env }
  const child =
    process.platform === 'win32'
      ? spawn('cmd.exe', ['/c', command, ...args], spawnOptions)
      : spawn(command, args, spawnOptions)

  child.on('error', (error) => {
    if (error.code === 'ENOENT' && index < npxCandidates.length - 1) {
      spawnNpx(index + 1)
      return
    }

    console.error(`Failed to start figma-mcp: ${error.message}`)
    process.exit(1)
  })

  child.on('exit', (code) => {
    process.exit(code ?? 0)
  })
}

spawnNpx()
