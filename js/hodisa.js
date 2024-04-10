/**
 * @author Anvar Zaripboyev
 */

const chiqar = document.querySelector("#chiqar");
const boshi = document.getElementById("boshi");
const oxiri = document.getElementById("oxiri");

// Tanlov
const SHAKL_KITOB = 1;
const SHAKL_OLDI_ORQA = 2;

// const shakl = document.getElementsByName("shakl");
const kitob = document.getElementById("kitob");
const ikkiTomonlama = document.getElementById("ikki-tomonli");

// Chiquvchilar
const old = document.getElementById("old");
const orqa = document.getElementById("orqa");

/**
 * @var x - Boshlanish
 * @var y - Tugash
 */
let x, y;


/**
 * Chiquvchi maydonlarni tozalash
 */
function maydonniTozala() {
    old.value = '';
    orqa.value = '';
}

function tekshir(qiymat, qaytaAloqa) {
    if (qiymat > Math.E ** 10) {
        qaytaAloqa();
    }
}

function nusxala(tugma, maydon) {
    // Nusxalash
    navigator.clipboard.writeText(maydon.value);

    tugma.classList.replace("far", "fas");
}

function boshqa(tugma) {
    tugma.classList.replace("fas", "far");
}

/**
 * Kitobcha shaklida
 * @param b - Boshlanish
 * @param t - Tugashi
 */
function shaklKitobcha(b, t) {
    /**
     * m Markaz, O'rta
     */
    let m = t - b + 1;

    let a = [];
    for (let i = 1; i <= m; i++) {
        a[i] = (b + i) - 1;
    }

    if (m % 4 === 0) {
        let son = m / 4;

        for (let i = m; i >= 1; i--) {
            if (i % 2 === 0) {
                if (son > 0) {
                    old.value += (a[i] + "," + (a[m - i + 1]) + (m / 2 + 2 === i ? "" : ","));
                } else {
                    orqa.value += (a[i] + "," + (a[m - i + 1]) + (i === 2 ? "" : ","));
                }
                son--;
            }

            console.log(a[i] + "," + (a[m - i + 1]) + ", " + i + " ");
        }
        alert("Jami: " + m / 4 + " varaq");
    } else {
        alert("Kitob shaklida chop etish uchun sahifalar soni \n" + "to\u2018rtga qoldiqsiz bo\u2018linuvchi bo\u2018lishi kerak!");
    }
}

/* 
 * Ikki tomonlama shakli
 * @param b - Boshlanish raqami
 * @param t - Tugash raqami
 */
function shaklIkkiTomonlama(b, t) {
    // m - Markaz
    let m = t - b + 1;

    if (m % 2 === 0) {
        for (let i = b; i <= t; i += 2) {
            old.value += (i + (i === t - 1 ? "" : ","));
        }
        for (let i = t; i >= b; i -= 2) {
            orqa.value += (i + (i === b + 1 ? "" : ","));
        }
    } else {
        alert("Sahifalar soni juft bo\u2018lishi kerak!");
    }
}


kitob.onchange = maydonniTozala;
ikkiTomonlama.onchange = maydonniTozala;

boshi.addEventListener("keyup", function (keyboardEvent) {
    var input = keyboardEvent.target;
    let qiymat = input.value;
    let son = true;
    tekshir(qiymat, function () {
        son = false;
        alert("Faqat raqam kiriting");
    });

    if (son) {
        x = parseInt(qiymat);
    }

    maydonniTozala();
});

oxiri.addEventListener("keyup", keyboardEvent => {
    var input = keyboardEvent.target;
    let qiymat = input.value;
    let son = true;
    tekshir(qiymat, function () {
        son = false;
        alert("Faqat raqam kiriting");
    });

    if (son) {
        y = parseInt(qiymat);
    }

    maydonniTozala();
});

chiqar.onclick = function () {
    maydonniTozala();

    if (x * y > 0) {
        if (kitob.checked) {
            shaklKitobcha(x, y);
        }

        if (ikkiTomonlama.checked) {
            shaklIkkiTomonlama(x, y);
        }
    } else {
        alert("Ma\u2019lumot kiritlmagan!");
    }
}
