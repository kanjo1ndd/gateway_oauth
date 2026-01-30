import { useEffect, useState } from 'react'

function App() {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetch('/profile', { credentials: 'include' })
      .then(res => {
        if (res.status === 401) {
          setLoading(false)
          return null
        }
        return res.json()
      })
      .then(data => {
        if (data) setUser(data)
        setLoading(false)
      })
      .catch(() => setLoading(false))
  }, [])

  const login = () => {
    window.location.href = '/oauth2/authorization/google'
  }

  if (loading) return <div>Loading...</div>

  if (!user) {
    return (
      <div>
        <h2>You are not logged in</h2>
        <button onClick={login}>Login with Google</button>
      </div>
    )
  }

  return (
    <div>
      <h2>Welcome</h2>
      <p>Name: {user.name}</p>
      <p>Email: {user.email}</p>
    </div>
  )
}

export default App