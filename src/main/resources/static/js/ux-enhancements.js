/**
 * UX Enhancements for ShopHub E-commerce Application
 */

// Toast Notification System
const Toast = {
    container: null,

    init() {
        if (!this.container) {
            this.container = document.createElement('div');
            this.container.className = 'toast-container';
            document.body.appendChild(this.container);
        }
    },

    show(message, type = 'info', duration = 3000) {
        this.init();

        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        toast.innerHTML = `
            <div style="display: flex; align-items: center; gap: 10px;">
                <i class="fas fa-${this.getIcon(type)}"></i>
                <span>${message}</span>
            </div>
        `;

        this.container.appendChild(toast);

        setTimeout(() => {
            toast.style.animation = 'slideInRight 0.3s ease reverse';
            setTimeout(() => toast.remove(), 300);
        }, duration);
    },

    getIcon(type) {
        const icons = {
            success: 'check-circle',
            error: 'exclamation-circle',
            info: 'info-circle',
            warning: 'exclamation-triangle'
        };
        return icons[type] || icons.info;
    },

    success(message) {
        this.show(message, 'success');
    },

    error(message) {
        this.show(message, 'error');
    },

    info(message) {
        this.show(message, 'info');
    }
};

// Button Loading State
function setButtonLoading(button, loading = true) {
    if (loading) {
        button.classList.add('btn-loading');
        button.dataset.originalText = button.innerHTML;
        button.disabled = true;
    } else {
        button.classList.remove('btn-loading');
        if (button.dataset.originalText) {
            button.innerHTML = button.dataset.originalText;
        }
        button.disabled = false;
    }
}

// Form Submit with Loading State
function enhanceForm(formSelector) {
    const forms = document.querySelectorAll(formSelector);
    forms.forEach(form => {
        form.addEventListener('submit', function (e) {
            const submitButton = this.querySelector('button[type="submit"]');
            if (submitButton) {
                setButtonLoading(submitButton, true);
            }
        });
    });
}

// Add to Cart with Feedback
function enhanceAddToCart() {
    // Enhance all "Add to Cart" buttons
    const addToCartForms = document.querySelectorAll('form[action="/addToCart"]');
    addToCartForms.forEach(form => {
        form.addEventListener('submit', function (e) {
            const button = this.querySelector('button[type="submit"]');
            if (button) {
                setButtonLoading(button, true);
                Toast.success('Item added to cart!');

                // Update cart badge
                setTimeout(() => {
                    updateCartBadge();
                }, 500);
            }
        });
    });
}

// Update Cart Badge
function updateCartBadge() {
    const badge = document.querySelector('.label');
    if (badge) {
        const currentCount = parseInt(badge.textContent) || 0;
        badge.textContent = currentCount + 1;
        badge.style.animation = 'pulse 0.3s';
    }
}

// Smooth Scroll to Top
function addScrollToTop() {
    const scrollBtn = document.createElement('button');
    scrollBtn.innerHTML = '<i class="fas fa-arrow-up"></i>';
    scrollBtn.className = 'scroll-to-top';
    scrollBtn.style.cssText = `
        position: fixed;
        bottom: 20px;
        right: 20px;
        background: #7386D5;
        color: white;
        border: none;
        border-radius: 50%;
        width: 50px;
        height: 50px;
        display: none;
        cursor: pointer;
        z-index: 1000;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        transition: all 0.3s ease;
    `;

    scrollBtn.addEventListener('click', () => {
        window.scrollTo({top: 0, behavior: 'smooth'});
    });

    window.addEventListener('scroll', () => {
        if (window.pageYOffset > 300) {
            scrollBtn.style.display = 'block';
        } else {
            scrollBtn.style.display = 'none';
        }
    });

    document.body.appendChild(scrollBtn);
}

// Image Lazy Loading
function addLazyLoading() {
    const images = document.querySelectorAll('img[data-src]');

    const imageObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                img.src = img.dataset.src;
                img.removeAttribute('data-src');
                observer.unobserve(img);
            }
        });
    });

    images.forEach(img => imageObserver.observe(img));
}

// Quantity Input Validation
function enhanceQuantityInputs() {
    const quantityInputs = document.querySelectorAll('input[type="number"][name="quantity"]');

    quantityInputs.forEach(input => {
        // Add +/- buttons
        const wrapper = document.createElement('div');
        wrapper.className = 'quantity-wrapper d-flex align-items-center';
        wrapper.style.cssText = 'gap: 5px;';

        const minusBtn = document.createElement('button');
        minusBtn.type = 'button';
        minusBtn.className = 'btn btn-sm btn-outline-secondary';
        minusBtn.innerHTML = '<i class="fas fa-minus"></i>';
        minusBtn.onclick = () => {
            const current = parseInt(input.value) || 1;
            const min = parseInt(input.min) || 1;
            if (current > min) {
                input.value = current - 1;
            }
        };

        const plusBtn = document.createElement('button');
        plusBtn.type = 'button';
        plusBtn.className = 'btn btn-sm btn-outline-secondary';
        plusBtn.innerHTML = '<i class="fas fa-plus"></i>';
        plusBtn.onclick = () => {
            const current = parseInt(input.value) || 1;
            const max = parseInt(input.max) || 999;
            if (current < max) {
                input.value = current + 1;
            }
        };

        input.parentNode.insertBefore(wrapper, input);
        wrapper.appendChild(minusBtn);
        wrapper.appendChild(input);
        wrapper.appendChild(plusBtn);
    });
}

// Confirm Delete Actions
function addDeleteConfirmation() {
    const deleteLinks = document.querySelectorAll('a[href*="remove"], a.remove-from-cart');

    deleteLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            if (!confirm('Are you sure you want to remove this item?')) {
                e.preventDefault();
            }
        });
    });
}

// Search Enhancement with Debounce
let searchTimeout;

function enhanceSearch() {
    const searchInput = document.getElementById('search');
    if (searchInput) {
        searchInput.addEventListener('input', function () {
            clearTimeout(searchTimeout);
            const value = this.value;

            if (value.length >= 2) {
                searchTimeout = setTimeout(() => {
                    // Show loading state
                    const dropdown = document.getElementById('dropdown-container');
                    if (dropdown) {
                        dropdown.innerHTML = '<div class="p-3 text-center"><i class="fas fa-spinner fa-spin"></i> Searching...</div>';
                        dropdown.classList.add('show');
                    }
                }, 300);
            }
        });
    }
}

// Initialize all enhancements when DOM is ready
document.addEventListener('DOMContentLoaded', function () {
    // Initialize enhancements
    enhanceAddToCart();
    enhanceForm('form');
    addScrollToTop();
    addDeleteConfirmation();
    enhanceSearch();

    // Show welcome message
    setTimeout(() => {
        Toast.info('Welcome to ShopHub! Happy Shopping!');
    }, 500);

    console.log('🛍️ ShopHub UX Enhancements Loaded');
});

// Make Toast available globally
window.Toast = Toast;
window.setButtonLoading = setButtonLoading;
