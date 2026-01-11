param(
    [int]$BookId = 1,       # ID du livre à tester
    [int]$Requests = 50     # Nombre de requêtes simultanées
)

# Instances book-service
$Ports = @(8081, 8083, 8084)

Write-Host "== TP27 Load test =="
Write-Host "BookId=$BookId Requests=$Requests"
Write-Host "Ports=$($Ports -join ',')"
Write-Host ""

# Tableau pour stocker les jobs PowerShell
$jobs = @()

# Lancer les requêtes en parallèle
for ($i=1; $i -le $Requests; $i++) {
    $port = $Ports[$i % $Ports.Count]
    $url = "http://localhost:$port/api/books/$BookId/borrow"

    $jobs += Start-Job -ScriptBlock {
        param($u, $p)
        try {
            $resp = Invoke-RestMethod -Uri $u -Method Post -ContentType "application/json"
            [PSCustomObject]@{ Port=$p; Status=200; Body=$resp }
        } catch {
            if ($_.Exception.Response -ne $null) {
                $status = $_.Exception.Response.StatusCode.value__
                $reader = New-Object IO.StreamReader($_.Exception.Response.GetResponseStream())
                $body = $reader.ReadToEnd()
                [PSCustomObject]@{ Port=$p; Status=$status; Body=$body }
            } else {
                [PSCustomObject]@{ Port=$p; Status=-1; Body=$_.Exception.Message }
            }
        }
    } -ArgumentList $url, $port
}

# Attendre fin de tous les jobs
$results = $jobs | Wait-Job | Receive-Job
$jobs | Remove-Job

# Compter Success / Conflict / Other
$success  = ($results | Where-Object {$_.Status -eq 200}).Count
$conflict = ($results | Where-Object {$_.Status -eq 409}).Count
$other    = $Requests - $success - $conflict

# Affichage des résultats
Write-Host "== Résultats =="
Write-Host "Success (200):  $success"
Write-Host "Conflict (409): $conflict"
Write-Host "Other:          $other"

# Optionnel : détails
# $results | Format-Table Port, Status, Body
